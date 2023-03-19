package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableStateOf
import xyz.tberghuis.floatingtimer.service.OverlayState


class StopwatchState {

  val overlayState = OverlayState()


  val timeElapsed = mutableStateOf(0)

  val running = mutableStateOf(false)

  fun resetStopwatchState() {
    timeElapsed.value = 0
    running.value = false
  }

}