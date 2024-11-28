package xyz.tberghuis.floatingtimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.room.Room
import xyz.tberghuis.floatingtimer.data.AppDatabase
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.data.dataStore
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.service.BoundService
import xyz.tberghuis.floatingtimer.service.FloatingService

class MainApplication : Application() {
  // could i just use Context.dataStore instead for singleton
  // i could write some tests to see if singleton across application
  // and activities
//  lateinit var appDatabase: AppDatabase
  lateinit var boundFloatingService: BoundService<FloatingService>

  lateinit var billingClientWrapper: BillingClientWrapper

  override fun onCreate() {
    super.onCreate()
//    appDatabase = provideDatabase()
    boundFloatingService = BoundService(this, FloatingService::class.java)
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

//  private fun provideDatabase(): AppDatabase {
//    return Room.databaseBuilder(
//      this,
//      AppDatabase::class.java,
//      DB_FILENAME
//    )
//      .build()
//  }
}

//fun Application.provideDatabase(): AppDatabase {
//  return (this as MainApplication).appDatabase
//}

fun Application.provideBillingClientWrapper(): BillingClientWrapper {
  return (this as MainApplication).billingClientWrapper
}