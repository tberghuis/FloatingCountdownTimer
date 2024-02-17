package xyz.tberghuis.floatingtimer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.tberghuis.floatingtimer.tmp4.TmpSavedCountdown
import xyz.tberghuis.floatingtimer.tmp4.TmpSavedCountdownDao
import xyz.tberghuis.floatingtimer.tmp4.TmpSavedStopwatch
import xyz.tberghuis.floatingtimer.tmp4.TmpSavedStopwatchDao

@Database(
  entities = [TmpSavedCountdown::class, TmpSavedStopwatch::class], version = 1, exportSchema = true,
  autoMigrations = [
//    AutoMigration(from = 1, to = 2)
  ]
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun savedCountdownDao(): TmpSavedCountdownDao
  abstract fun savedStopwatchDao(): TmpSavedStopwatchDao
}