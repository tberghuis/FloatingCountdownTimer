package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.service.OverlayState

class StopwatchState {
  val overlayState = OverlayState()
  val timeElapsed = mutableStateOf(0)
  val running = MutableStateFlow(false)

  fun resetStopwatchState() {
    timeElapsed.value = 0
    running.value = false
  }
}