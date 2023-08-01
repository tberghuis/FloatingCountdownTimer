package xyz.tberghuis.floatingtimer.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import xyz.tberghuis.floatingtimer.service.countdown.CountdownState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState

class ServiceState(scope: CoroutineScope) {
  val countdownState = CountdownState()
  val stopwatchState = StopwatchState(scope)

  val configurationChanged = MutableSharedFlow<Unit>()
}