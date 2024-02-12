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
data class TmpSavedTimer(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "type") val timerType: String,
  @ColumnInfo(name = "shape") val timerShape: String,
  // Color.toArgb
  @ColumnInfo(name = "color") val timerColor: Int,
  // stores as seconds
  @ColumnInfo(name = "duration") val durationSeconds: Int? = null
)

@Dao
interface TmpSavedTimerDao {
  @Query("SELECT * FROM TmpSavedTimer")
  fun getAll(): Flow<List<TmpSavedTimer>>

  @Insert
  fun insertAll(vararg timers: TmpSavedTimer)

  @Delete
  fun delete(timer: TmpSavedTimer)
}
