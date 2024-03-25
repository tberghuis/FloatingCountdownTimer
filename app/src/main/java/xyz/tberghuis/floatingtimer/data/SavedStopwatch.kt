package xyz.tberghuis.floatingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class SavedStopwatch(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "shape") override val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") override val timerColor: Int,
  @ColumnInfo() val label: String? = null,
) : SavedTimer

@Dao
interface SavedStopwatchDao {
  @Query("SELECT * FROM SavedStopwatch")
  fun getAll(): Flow<List<SavedStopwatch>>

  @Insert
  fun insertAll(vararg timers: SavedStopwatch)

  @Delete
  fun delete(timer: SavedStopwatch)
}