package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase

class TmpCountdownScreenVm(private val application: Application) : AndroidViewModel(application) {

  private val savedTimerDao = application.provideDatabase().tmpSavedTimerDao()

  // store savedTimer.id
  var showDeleteDialog by mutableStateOf<Int?>(null)


  init {
  }

  fun savedTimerFlow(): Flow<List<TmpSavedTimer>> {
    return savedTimerDao.getAll()
  }


  fun addToSaved() {
    tmp1()
  }

  fun tmp1() {
    val t1 = TmpSavedTimer(
      timerType = "countdown",
      timerShape = "circle",
      timerColor = "ffffffff"
    )
    viewModelScope.launch(IO) {
      savedTimerDao.insertAll(t1)
    }
  }

  fun tmp2() {
    viewModelScope.launch(IO) {
      val all = savedTimerDao.getAll()
      logd("timers all: $all")
    }
  }
}
