package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.service.boundFloatingService

class SharedVm(application: Application) : AndroidViewModel(application) {
  private val boundFloatingService = application.boundFloatingService
  var showGrantOverlayDialog by mutableStateOf(false)
  fun cancelAllTimers() {
    viewModelScope.launch {
      boundFloatingService.provideService().overlayController.exitAll()
    }
  }

  fun saveTimerPositions() {
    viewModelScope.launch {
      boundFloatingService.provideService().overlayController.saveTimerPositions()
    }
  }
}