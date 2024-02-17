package xyz.tberghuis.floatingtimer.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [SavedCountdown::class, SavedStopwatch::class], version = 1, exportSchema = true,
  autoMigrations = [
//    AutoMigration(from = 1, to = 2)
  ]
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun savedCountdownDao(): SavedCountdownDao
  abstract fun savedStopwatchDao(): SavedStopwatchDao
}