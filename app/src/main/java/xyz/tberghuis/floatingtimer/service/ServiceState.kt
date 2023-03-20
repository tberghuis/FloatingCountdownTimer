package xyz.tberghuis.floatingtimer.service

import xyz.tberghuis.floatingtimer.service.countdown.CountdownState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState

class ServiceState {
  val countdownState = CountdownState()
  val stopwatchState = StopwatchState()

  // these could be moved back into OverlayController
  // meh
  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE
}