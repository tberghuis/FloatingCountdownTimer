package xyz.tberghuis.floatingtimer.service

import kotlinx.coroutines.CoroutineScope
import xyz.tberghuis.floatingtimer.service.countdown.CountdownState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState

class ServiceState(scope: CoroutineScope) {
  val countdownState = CountdownState()
  val stopwatchState = StopwatchState(scope)

  // these could be moved back into OverlayController
  // meh


  // todo refactor use ScreenEz.safeWidth
  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE
}