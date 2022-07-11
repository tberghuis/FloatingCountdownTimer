package xyz.tberghuis.floatingtimer.stopwatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset

// make sure use like
// state.willitblend

// find all StopwatchStateHolder import
// replace with import StopwatchStateHolder as state
// delete all import StopwatchStateHolder.*

//object StopwatchStateHolder {
//  val timeElapsed = mutableStateOf(0)
//
//  val running = mutableStateOf(false)
//}

class StopwatchState {
  val timeElapsed = mutableStateOf(0)

  val running = mutableStateOf(false)

  fun resetStopwatchState() {
    timeElapsed.value = 0
    running.value = false
  }

}

// todo move to member of StopwatchOverlayComponent
val stopwatchState = StopwatchState()


// todo fn resetStopwatchState
// call from service onstartcommand
