package xyz.tberghuis.floatingtimer.tmp4

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class TmpSavedTimer(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "type") val timerType: String,
  @ColumnInfo(name = "shape") val timerShape: String,
  @ColumnInfo(name = "color") val timerColor: String
)

@Dao
interface TmpSavedTimerDao {
  @Query("SELECT * FROM TmpSavedTimer")
  fun getAll(): List<TmpSavedTimer>

  @Insert
  fun insertAll(vararg timers: TmpSavedTimer)

  @Delete
  fun delete(timer: TmpSavedTimer)
}
