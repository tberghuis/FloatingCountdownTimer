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
}

val stopwatchState = StopwatchState()


class OverlayState {
  var timerOffset by mutableStateOf(IntOffset.Zero)
}

val stopwatchOverlayState = OverlayState()


// todo fn resetStopwatchState
// call from service onstartcommand
