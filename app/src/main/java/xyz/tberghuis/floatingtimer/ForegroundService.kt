package xyz.tberghuis.floatingtimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import xyz.tberghuis.floatingtimer.OverlayStateHolder.durationSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.pendingAlarm
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerState
import xyz.tberghuis.floatingtimer.receivers.CountdownResetReceiver
import xyz.tberghuis.floatingtimer.receivers.ExitReceiver
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : Service() {
  @Inject
  lateinit var overlayComponent: OverlayComponent

  private val binder = LocalBinder()

  inner class LocalBinder : Binder() {
    fun getService(): ForegroundService = this@ForegroundService
  }

  // can probably return null because i don't need direct communication with activity
  override fun onBind(intent: Intent?): IBinder {
    return binder
  }

  private fun createNotificationChannel() {
    val mChannel = NotificationChannel(
      CHANNEL_DEFAULT_ID,
      CHANNEL_DEFAULT_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )
    mChannel.description = CHANNEL_DEFAULT_DESCRIPTION
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
  }

  override fun onStartCommand(intentOrNull: Intent?, flags: Int, startId: Int): Int {
    logd("onStartCommand intentOrNull $intentOrNull")

    intentOrNull?.let { intent ->
      val command = intent.getStringExtra(INTENT_COMMAND)
      logd("command $command")

      when (command) {
        INTENT_COMMAND_EXIT -> {
          pendingAlarm?.cancel()
          overlayComponent.endService()
          return START_NOT_STICKY
        }
        INTENT_COMMAND_RESET -> {
          pendingAlarm?.cancel()
          resetTimerState(durationSeconds)
        }
        INTENT_COMMAND_CREATE_TIMER -> {
          val duration = intent.getIntExtra(EXTRA_TIMER_DURATION, 10)
          logd("INTENT_COMMAND_CREATE_TIMER duration $duration")
          pendingAlarm?.cancel()
          resetTimerState(duration)
          // todo position timer default position
          overlayComponent.showOverlay()
        }
        INTENT_COMMAND_COUNTDOWN_COMPLETE -> {
          logd("onStartCommand INTENT_COMMAND_COUNTDOWN_COMPLETE")
          timerState.value = TimerStateFinished
        }
      }
    }

    createNotificationChannel()

    val pendingIntent: PendingIntent =
      Intent(this, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
      }

    // todo shouldn't need BroadcastReceiver
    // https://stackoverflow.com/questions/73344244/how-to-invoke-a-method-on-a-foreground-service-by-clicking-on-a-notification-act

    val exitIntent = Intent(applicationContext, ExitReceiver::class.java)
    val exitPendingIntent = PendingIntent.getBroadcast(
      applicationContext,
      REQUEST_CODE_EXIT_COUNTDOWN, exitIntent, FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, CountdownResetReceiver::class.java)
    val resetPendingIntent = PendingIntent.getBroadcast(
      applicationContext,
      REQUEST_CODE_RESET_COUNTDOWN, resetIntent, FLAG_IMMUTABLE
    )

    val notification: Notification = NotificationCompat.Builder(this, CHANNEL_DEFAULT_ID)
      .setContentTitle("Floating Countdown Timer")
//      .setContentText("content text service running")
      .setSmallIcon(R.drawable.ic_alarm)
      .setContentIntent(pendingIntent)
//      .setTicker("ticker service running")
      .addAction(
        0, "Reset", resetPendingIntent
      )
      .addAction(
        0, "Exit", exitPendingIntent
      )
      .build()

    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, notification)

    return START_STICKY
  }

  override fun onDestroy() {
    super.onDestroy()
    logd("onDestroy")
  }

/*  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    logd("onConfigurationChanged $newConfig")
  }*/
}