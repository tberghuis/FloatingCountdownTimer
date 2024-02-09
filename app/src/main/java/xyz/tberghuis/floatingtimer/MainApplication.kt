package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.room.Room
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.data.dataStore
import xyz.tberghuis.floatingtimer.tmp4.TmpAppDatabase

class MainApplication : Application() {

  // could i just use Context.dataStore instead for singleton
  // i could write some tests to see if singleton across application
  // and activities
  // doitwrong
  lateinit var preferencesRepository: PreferencesRepository

  lateinit var appDatabase: TmpAppDatabase


  override fun onCreate() {
    super.onCreate()
    appDatabase = provideDatabase()
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

  private fun provideDatabase(): TmpAppDatabase {
    return Room.databaseBuilder(
      this,
      TmpAppDatabase::class.java,
      DB_FILENAME
    )
      .build()
  }


}

// doitwrong
fun Application.providePreferencesRepository(): PreferencesRepository {
  return (this as MainApplication).preferencesRepository
}

fun Application.provideDatabase(): TmpAppDatabase {
  return (this as MainApplication).appDatabase
}