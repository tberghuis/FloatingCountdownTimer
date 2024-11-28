package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper

class MainApplication : Application() {
  // could i just use Context.dataStore instead for singleton
  // i could write some tests to see if singleton across application
  // and activities

  lateinit var billingClientWrapper: BillingClientWrapper

  override fun onCreate() {
    super.onCreate()
    createNotificationChannel()
    billingClientWrapper = BillingClientWrapper(this)
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

fun Application.provideBillingClientWrapper(): BillingClientWrapper {
  return (this as MainApplication).billingClientWrapper
}