package xyz.tberghuis.floatingtimer.data

import android.app.Application
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.tberghuis.floatingtimer.DB_FILENAME

@Database(
  entities = [SavedCountdown::class, SavedStopwatch::class], version = 4, exportSchema = true,
  autoMigrations = [
    AutoMigration(from = 1, to = 2),
    AutoMigration(from = 2, to = 3),
    AutoMigration(from = 3, to = 4)
  ]
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun savedCountdownDao(): SavedCountdownDao
  abstract fun savedStopwatchDao(): SavedStopwatchDao

  companion object {
    @Volatile
    private var instance: AppDatabase? = null
    fun getInstance(application: Application) =
      instance ?: synchronized(this) {
        instance ?: Room.databaseBuilder(
          application,
          AppDatabase::class.java,
          DB_FILENAME
        )
          .build()
          .also { instance = it }
      }
  }
}

val Context.appDatabase: AppDatabase
  get() = AppDatabase.getInstance(this.applicationContext as Application)