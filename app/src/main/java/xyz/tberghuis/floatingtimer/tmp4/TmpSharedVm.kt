package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.logd

class TmpSharedVm(private val application: Application) : AndroidViewModel(application) {
  private val boundFloatingService = (application as MainApplication).boundFloatingService

  var showGrantOverlayDialog by mutableStateOf(false)

  init {
    logd("TmpSharedVm init, test, does it re-init on screen rotate")
  }

  fun cancelAllTimers() {
    viewModelScope.launch {
      boundFloatingService.provideFloatingService().overlayController.exitAll()
    }
  }
}