package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase

class TmpCountdownScreenVm(private val application: Application) : AndroidViewModel(application) {

  private val savedTimerDao = application.provideDatabase().tmpSavedTimerDao()

  fun addToSaved() {

  }

  fun tmp1() {
    val t1 = TmpSavedTimer(
      timerType = "countdown",
      timerShape = "circle"
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
