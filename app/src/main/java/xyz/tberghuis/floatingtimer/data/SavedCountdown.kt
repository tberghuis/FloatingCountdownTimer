package xyz.tberghuis.floatingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.tberghuis.floatingtimer.tmp4.SavedTimer

@Entity
data class SavedCountdown(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "shape") val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") override val timerColor: Int,
  // stores as seconds
  @ColumnInfo(name = "duration") val durationSeconds: Int
) : SavedTimer

@Dao
interface TmpSavedCountdownDao {
  @Query("SELECT * FROM SavedCountdown")
  fun getAll(): Flow<List<SavedCountdown>>

  @Insert
  fun insertAll(vararg timers: SavedCountdown)

  @Delete
  fun delete(timer: SavedCountdown)
}