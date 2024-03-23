package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.viewmodels.TimerShapeChoiceVm

class TmpStopwatchScreenVm(
  private val application: Application,
) : AndroidViewModel(application), TimerShapeChoiceVm {
  private val boundFloatingService = (application as MainApplication).boundFloatingService
  var haloColor by mutableStateOf(DEFAULT_HALO_COLOR)

  // todo label
  override var timerShape by mutableStateOf("circle")

  private fun addStopwatch(haloColor: Color, timerShape: String) {
    viewModelScope.launch {
      boundFloatingService.provideFloatingService().overlayController.addStopwatch(
        haloColor, timerShape
      )
    }
  }

  fun stopwatchButtonClick() {
    addStopwatch(haloColor, timerShape)
  }
}