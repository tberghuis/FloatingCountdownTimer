package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.room.Room
import xyz.tberghuis.floatingtimer.data.AppDatabase
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.data.dataStore
import xyz.tberghuis.floatingtimer.viewmodels.BoundFloatingServiceVmc

class MainApplication : Application() {
  // could i just use Context.dataStore instead for singleton
  // i could write some tests to see if singleton across application
  // and activities
  // doitwrong
  lateinit var preferencesRepository: PreferencesRepository
  lateinit var appDatabase: AppDatabase
  lateinit var boundFloatingService: BoundFloatingServiceVmc

  override fun onCreate() {
    super.onCreate()
    appDatabase = provideDatabase()
    preferencesRepository = PreferencesRepository(dataStore)
    boundFloatingService = BoundFloatingServiceVmc(this)
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

  private fun provideDatabase(): AppDatabase {
    return Room.databaseBuilder(
      this,
      AppDatabase::class.java,
      DB_FILENAME
    )
      .build()
  }


}

// doitwrong
fun Application.providePreferencesRepository(): PreferencesRepository {
  return (this as MainApplication).preferencesRepository
}

fun Application.provideDatabase(): AppDatabase {
  return (this as MainApplication).appDatabase
}