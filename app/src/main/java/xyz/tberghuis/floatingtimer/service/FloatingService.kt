package xyz.tberghuis.floatingtimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import xyz.tberghuis.floatingtimer.EXTRA_COUNTDOWN_DURATION
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_COMPLETE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_CREATE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_CREATE
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL_DISPLAY
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.ExitReceiver
import xyz.tberghuis.floatingtimer.receivers.ResetReceiver
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished

class FloatingService : Service() {
  val state = ServiceState()
  private var isStarted = false

  // todo make private
  lateinit var overlayController: OverlayController
  lateinit var alarmController: AlarmController

  override fun onCreate() {
    super.onCreate()
    overlayController = OverlayController(this)
    alarmController = AlarmController(this)
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("FloatingService onStartCommand")
    postOngoingActivityNotification()

    intent?.let {
      val command = intent.getStringExtra(INTENT_COMMAND)
      when (command) {
        INTENT_COMMAND_COUNTDOWN_CREATE -> {
          // todo cancel pending alarm
          val duration = intent.getIntExtra(EXTRA_COUNTDOWN_DURATION, 10)
          state.countdownState.resetTimerState(duration)
          state.countdownState.overlayState.isVisible.value = true
        }
        INTENT_COMMAND_COUNTDOWN_COMPLETE -> {
          logd("onStartCommand INTENT_COMMAND_COUNTDOWN_COMPLETE")
          state.countdownState.timerState.value = TimerStateFinished
        }
        INTENT_COMMAND_STOPWATCH_CREATE -> {
          state.stopwatchState.overlayState.isVisible.value = true
        }
        INTENT_COMMAND_EXIT -> {
          if (state.countdownState.overlayState.isVisible.value == true) {
            overlayController.exitCountdown()
          }
          if (state.stopwatchState.overlayState.isVisible.value == true) {
            overlayController.exitStopwatch()
          }
          return START_NOT_STICKY
        }
        INTENT_COMMAND_RESET -> {
          state.stopwatchState.resetStopwatchState()
          state.countdownState.resetTimerState()
        }
      }
    }
    return START_STICKY
  }

  private fun postOngoingActivityNotification() {
    if (!isStarted) {
      isStarted = true
      createNotificationChannel()
      startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
    }
  }

  private fun createNotificationChannel() {
    val notificationChannel = NotificationChannel(
      NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_DISPLAY, NotificationManager.IMPORTANCE_DEFAULT
    )
    val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(notificationChannel)
  }

  private fun buildNotification(): Notification {
    val pendingIntent: PendingIntent =
      Intent(this, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
      }

    // todo shouldn't need BroadcastReceiver
    // https://stackoverflow.com/questions/73344244/how-to-invoke-a-method-on-a-foreground-service-by-clicking-on-a-notification-act

    val exitIntent = Intent(applicationContext, ExitReceiver::class.java)
    val exitPendingIntent = PendingIntent.getBroadcast(
      applicationContext, REQUEST_CODE_EXIT, exitIntent, FLAG_IMMUTABLE
    )
    val resetIntent = Intent(applicationContext, ResetReceiver::class.java)
    val resetPendingIntent = PendingIntent.getBroadcast(
      applicationContext, REQUEST_CODE_RESET, resetIntent, FLAG_IMMUTABLE
    )
    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("Floating Timer")
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).addAction(
          0, "Reset", resetPendingIntent
        ).addAction(
          0, "Exit", exitPendingIntent
        ).build()
    return notification
  }
}