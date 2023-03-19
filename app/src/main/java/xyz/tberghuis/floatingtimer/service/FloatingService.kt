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
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL_DISPLAY
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.logd

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
    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("Floating Timer")
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent)
        .build()
    return notification
  }


}