package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.data.dataStore


class MainApplication : Application() {

  // could i just use Context.dataStore instead for singleton
  // i could write some tests to see if singleton across application
  // and activities
  // doitwrong
  lateinit var preferencesRepository: PreferencesRepository

  override fun onCreate() {
    super.onCreate()
    preferencesRepository = PreferencesRepository(dataStore)
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

// doitwrong
fun Application.providePreferencesRepository(): PreferencesRepository {
  return (this as MainApplication).preferencesRepository
}