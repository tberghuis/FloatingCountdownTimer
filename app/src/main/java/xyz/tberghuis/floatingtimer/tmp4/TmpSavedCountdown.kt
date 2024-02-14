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
data class TmpSavedCountdown(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "shape") val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") val timerColor: Int,
  // stores as seconds
  @ColumnInfo(name = "duration") val durationSeconds: Int
)

@Dao
interface TmpSavedCountdownDao {
  @Query("SELECT * FROM TmpSavedCountdown")
  fun getAll(): Flow<List<TmpSavedCountdown>>

  @Insert
  fun insertAll(vararg timers: TmpSavedCountdown)

  @Delete
  fun delete(timer: TmpSavedCountdown)
}
