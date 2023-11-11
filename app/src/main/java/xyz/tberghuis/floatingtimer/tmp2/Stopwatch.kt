package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

class Stopwatch(service: FloatingService) {
  val overlayState = OverlayState()
  val viewHolder = TimerViewHolder(service)

  val timeElapsed = mutableStateOf(0)
  val isRunningStateFlow = MutableStateFlow(false)
  var stopwatchIncrementTask: TimerTask? = null

  fun resetStopwatchState() {
    timeElapsed.value = 0
    isRunningStateFlow.value = false
    stopwatchIncrementTask?.cancel()
    stopwatchIncrementTask = null
  }

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