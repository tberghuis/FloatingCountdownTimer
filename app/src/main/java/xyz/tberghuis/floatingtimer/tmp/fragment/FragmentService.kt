package xyz.tberghuis.floatingtimer.tmp.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL_DISPLAY
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd


class FragmentService : Service() {

  override fun onCreate() {
    super.onCreate()

  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("FragmentService onStartCommand")

    createNotificationChannel()
    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
    return START_STICKY
  }


  private fun createNotificationChannel() {
    val notificationChannel = NotificationChannel(
      NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_DISPLAY, NotificationManager.IMPORTANCE_DEFAULT
    )
    // channel description???
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
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).build()
    return notification
  }


}