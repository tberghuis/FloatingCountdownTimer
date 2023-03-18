package xyz.tberghuis.floatingtimer.countdown

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.EXTRA_COUNTDOWN_DURATION
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_COMPLETE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_CREATE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_RESET
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL_DISPLAY
import xyz.tberghuis.floatingtimer.OverlayComponent
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT_COUNTDOWN
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET_COUNTDOWN
import xyz.tberghuis.floatingtimer.common.OverlayStateFDSFSDF
import xyz.tberghuis.floatingtimer.receivers.CountdownResetReceiver
import xyz.tberghuis.floatingtimer.receivers.ExitReceiver
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver

@AndroidEntryPoint
class CountdownServiceFDSFSDFSD : Service() {

  private val overlayState = OverlayStateFDSFSDF()
  private var pendingAlarm: PendingIntent? = null

  @Inject
  lateinit var countdownState: CountdownStateVFDVDFV


  private lateinit var overlayComponent: OverlayComponent


  override fun onCreate() {
    super.onCreate()
    logd("ForegroundService onCreate")
    alarmSetup()
    overlayComponent = OverlayComponent(this, overlayState, countdownState) {
      // todo test older api levels than 32
      // stopForeground(STOP_FOREGROUND_REMOVE)
      stopSelf()
      pendingAlarm?.cancel()
    }

  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  private fun createNotificationChannel() {
    val mChannel = NotificationChannel(
      NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_DISPLAY, NotificationManager.IMPORTANCE_DEFAULT
    )
//    mChannel.description = CHANNEL_DEFAULT_DESCRIPTION
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
  }

  override fun onStartCommand(intentOrNull: Intent?, flags: Int, startId: Int): Int {
    logd("onStartCommand intentOrNull $intentOrNull")

    intentOrNull?.let { intent ->
      val command = intent.getStringExtra(INTENT_COMMAND)
      logd("command $command")

      when (command) {
        INTENT_COMMAND_COUNTDOWN_EXIT -> {
          pendingAlarm?.cancel()
          overlayComponent.endService()
          return START_NOT_STICKY
        }
        INTENT_COMMAND_COUNTDOWN_RESET -> {
          pendingAlarm?.cancel()
          countdownState.resetTimerState(countdownState.durationSeconds)
        }
        INTENT_COMMAND_COUNTDOWN_CREATE -> {
          val duration = intent.getIntExtra(EXTRA_COUNTDOWN_DURATION, 10)
          logd("INTENT_COMMAND_CREATE_TIMER duration $duration")
          pendingAlarm?.cancel()
          countdownState.resetTimerState(duration)
          overlayComponent.showOverlay()
        }
        INTENT_COMMAND_COUNTDOWN_COMPLETE -> {
          logd("onStartCommand INTENT_COMMAND_COUNTDOWN_COMPLETE")
          countdownState.timerState.value = TimerStateFinished
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
      applicationContext, REQUEST_CODE_EXIT_COUNTDOWN, exitIntent, FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, CountdownResetReceiver::class.java)
    val resetPendingIntent = PendingIntent.getBroadcast(
      applicationContext, REQUEST_CODE_RESET_COUNTDOWN, resetIntent, FLAG_IMMUTABLE
    )

    val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
      .setContentTitle("Floating Countdown Timer")
//      .setContentText("content text service running")
      .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent)
//      .setTicker("ticker service running")
      .addAction(
        0, "Reset", resetPendingIntent
      ).addAction(
        0, "Exit", exitPendingIntent
      ).build()

    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, notification)

    return START_STICKY
  }

  override fun onDestroy() {
    super.onDestroy()
    logd("onDestroy")
  }

  private fun alarmSetup() {
    val context = this@CountdownServiceFDSFSDFSD

    CoroutineScope(Dispatchers.Default).launch {
      countdownState.timerState.collect { timerState ->

        when (timerState) {
          TimerStateRunning -> {
            // set alarm
            logd("todo: run the timer")
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            // todo do i need to save pendingAlarm to state???
            pendingAlarm = PendingIntent.getBroadcast(
              context.applicationContext,
              REQUEST_CODE_PENDING_ALARM,
              intent,
              PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setAlarmClock(
              AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (countdownState.countdownSeconds * 1000), pendingAlarm
              ), pendingAlarm
            )
          }
          else -> {
            pendingAlarm?.cancel()
          }
        }
      }
    }
  }
}