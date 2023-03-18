package xyz.tberghuis.floatingtimer.stopwatch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import xyz.tberghuis.floatingtimer.CHANNEL_STOPWATCH_DESCRIPTION
import xyz.tberghuis.floatingtimer.CHANNEL_STOPWATCH_ID
import xyz.tberghuis.floatingtimer.CHANNEL_STOPWATCH_NAME
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_CREATE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_RESET
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT_STOPWATCH_SERVICE
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET_STOPWATCH_SERVICE
import xyz.tberghuis.floatingtimer.SERVICE_STOPWATCH_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.common.OverlayStateFDSFSDF
import xyz.tberghuis.floatingtimer.logd

@AndroidEntryPoint
class StopwatchService : Service() {

  val stopwatchOverlayState = OverlayStateFDSFSDF()
  lateinit var stopwatchOverlayComponent: StopwatchOverlayComponent
  val stopwatchState = StopwatchState()


  override fun onCreate() {
    super.onCreate()
    logd("ForegroundService onCreate")
    stopwatchOverlayComponent =
      StopwatchOverlayComponent(this, stopwatchOverlayState, stopwatchState)
  }


  override fun onBind(intent: Intent?): IBinder? {
    logd("onbind")
    return null
  }

  override fun onStartCommand(intentOrNull: Intent?, flags: Int, startId: Int): Int {
    logd("onstartcommand")

    intentOrNull?.let { intent ->
      val command = intent.getStringExtra(INTENT_COMMAND)
      logd("command $command")

      when (command) {

        INTENT_COMMAND_STOPWATCH_EXIT -> {
          stopwatchExit(stopwatchOverlayComponent, stopwatchState)
          return START_NOT_STICKY
        }
        INTENT_COMMAND_STOPWATCH_RESET -> {
          stopwatchState.resetStopwatchState()
        }

        INTENT_COMMAND_STOPWATCH_CREATE -> {
          stopwatchOverlayState.reset()
          stopwatchOverlayComponent.showOverlays()
        }
      }
    }

    createNotificationChannel()
    val notification: Notification = buildNotification()
    startForeground(SERVICE_STOPWATCH_NOTIFICATION_ID, notification)
//    return START_NOT_STICKY
    return START_STICKY
  }

  private fun buildNotification(): Notification {

    val exitIntent = Intent(applicationContext, StopwatchExitReceiver::class.java)
    val exitPendingIntent = PendingIntent.getBroadcast(
      applicationContext,
      REQUEST_CODE_EXIT_STOPWATCH_SERVICE, exitIntent, FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, StopwatchResetReceiver::class.java)
    val resetPendingIntent = PendingIntent.getBroadcast(
      applicationContext,
      REQUEST_CODE_RESET_STOPWATCH_SERVICE, resetIntent, FLAG_IMMUTABLE
    )

    return NotificationCompat.Builder(this, CHANNEL_STOPWATCH_ID)
      .setContentTitle("Floating Stopwatch")
      .setSmallIcon(R.drawable.ic_alarm)
      // this does nothing on > gingerbread
//      .setContentIntent(pendingIntent)
      .addAction(0, "Reset", resetPendingIntent)
      .addAction(0, "Exit", exitPendingIntent)
      .build()
  }

  // todo refactor
  private fun createNotificationChannel() {
    val mChannel = NotificationChannel(
      CHANNEL_STOPWATCH_ID,
      CHANNEL_STOPWATCH_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )
    mChannel.description = CHANNEL_STOPWATCH_DESCRIPTION
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
  }
}