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
data class SavedCountdown(
  @PrimaryKey(autoGenerate = true) override val id: Int = 0,
  @ColumnInfo(name = "shape") override val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") override val timerColor: Int,
  // stores as seconds
  @ColumnInfo(name = "duration") val durationSeconds: Int,
  @ColumnInfo() override val label: String? = null,
  @ColumnInfo(defaultValue = "false") override val isBackgroundTransparent: Boolean = false,
  override val positionX: Int? = null,
  override val positionY: Int? = null,
) : SavedTimer

@Dao
interface SavedCountdownDao {
  @Query("SELECT * FROM SavedCountdown")
  fun getAll(): Flow<List<SavedCountdown>>

  @Insert
  fun insertAll(vararg timers: SavedCountdown)

  @Delete
  fun delete(timer: SavedCountdown)

  @Update
  fun update(timer: SavedCountdown): Int

  @Query("SELECT * FROM SavedCountdown WHERE id = :id")
  suspend fun getById(id: Int): SavedCountdown?
}