package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.BoundFloatingServiceVmc

class TmpSharedVm(private val application: Application) : AndroidViewModel(application) {
//  private val boundFloatingServiceVmc = BoundFloatingServiceVmc(application)


  init {
    logd("TmpSharedVm init, test, does it re-init on screen rotate")
  }

  fun cancelAllTimers() {
    viewModelScope.launch {
      // todo
      // application.boundFloatingService.provideFloatingService().overlayController.exitAll()

    }
  }


}