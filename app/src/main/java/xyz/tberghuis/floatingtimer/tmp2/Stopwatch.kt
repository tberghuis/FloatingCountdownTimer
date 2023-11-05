package xyz.tberghuis.floatingtimer.tmp2

import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState

class Stopwatch(service: FloatingService)  {
  val stopwatchState = StopwatchState(service.scope)
}