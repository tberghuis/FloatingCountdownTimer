package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableStateOf
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.OverlayState

// todo remove xxx
class StopwatchState(scope: CoroutineScope) {
  val overlayState = OverlayState()
  val timeElapsed = mutableStateOf(0)
  val isRunningStateFlow = MutableStateFlow(false)
  var stopwatchIncrementTask: TimerTask? = null

  fun resetStopwatchState() {
    timeElapsed.value = 0
    isRunningStateFlow.value = false
    stopwatchIncrementTask?.cancel()
    stopwatchIncrementTask = null
  }

  init {
    scope.launch {
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