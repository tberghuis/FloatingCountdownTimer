package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.service.OverlayState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState
import java.util.TimerTask

class Stopwatch(service: FloatingService) {
  //  val stopwatchState = StopwatchState(service.scope)
  val overlayState = OverlayState()
  val viewHolder = TimerViewHolder(service)

  val timeElapsed = mutableStateOf(0)
  val isRunningStateFlow = MutableStateFlow(false)
  var stopwatchIncrementTask: TimerTask? = null


}