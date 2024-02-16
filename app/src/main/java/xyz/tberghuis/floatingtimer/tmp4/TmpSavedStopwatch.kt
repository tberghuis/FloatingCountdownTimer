package xyz.tberghuis.floatingtimer.tmp4

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class TmpSavedStopwatch(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "shape") val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") val timerColor: Int,
)

@Dao
interface TmpSavedStopwatchDao {
  @Query("SELECT * FROM TmpSavedStopwatch")
  fun getAll(): Flow<List<TmpSavedStopwatch>>

  @Insert
  fun insertAll(vararg timers: TmpSavedStopwatch)

  @Delete
  fun delete(timer: TmpSavedStopwatch)
}