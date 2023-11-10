package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.OverlayState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

class Stopwatch(service: FloatingService) {
  //  val stopwatchState = StopwatchState(service.scope)
  val overlayState = OverlayState()
  val viewHolder = TimerViewHolder(service)

  val timeElapsed = mutableStateOf(0)
  val isRunningStateFlow = MutableStateFlow(false)
  var stopwatchIncrementTask: TimerTask? = null


  // doitwrong
  init {
    service.scope.launch {
      isRunningStateFlow.collect { running ->
        when (running) {
          true -> {
            stopwatchIncrementTask = timerTask {
              timeElapsed.value++
            }
            Timer().scheduleAtFixedRate(stopwatchIncrementTask, 1000, 1000)
          }

          false -> {
            stopwatchIncrementTask?.cancel()
            stopwatchIncrementTask = null
          }
        }
      }
    }
  }


}