package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    createNotificationChannel()
  }

  private fun createNotificationChannel() {
    val notificationChannel = NotificationChannel(
      NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_DISPLAY, NotificationManager.IMPORTANCE_DEFAULT
    )
    // channel description???
    val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(notificationChannel)
  }
}