package xyz.tberghuis.floatingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class SavedStopwatch(
  @PrimaryKey(autoGenerate = true) override val id: Int = 0,
  @ColumnInfo(name = "shape") override val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") override val timerColor: Int,
  @ColumnInfo() override val label: String? = null,
  @ColumnInfo(defaultValue = "false") override val isBackgroundTransparent: Boolean = false,
  override val positionX: Int? = null,
  override val positionY: Int? = null,
) : SavedTimer

@Dao
interface SavedStopwatchDao {
  @Query("SELECT * FROM SavedStopwatch")
  fun getAll(): Flow<List<SavedStopwatch>>

  @Insert
  fun insertAll(vararg timers: SavedStopwatch)

  @Delete
  fun delete(timer: SavedStopwatch)

  @Update
  fun update(timer: SavedStopwatch): Int
}