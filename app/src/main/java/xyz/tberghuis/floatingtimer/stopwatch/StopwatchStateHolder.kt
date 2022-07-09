package xyz.tberghuis.floatingtimer.stopwatch

import androidx.compose.runtime.mutableStateOf

// make sure use like
// state.willitblend

// find all StopwatchStateHolder import
// replace with import StopwatchStateHolder as state
// delete all import StopwatchStateHolder.*

object StopwatchStateHolder {
  val timeElapsed = mutableStateOf(0)

  var running = false

}

// todo fn resetStopwatchState
// call from service onstartcommand
