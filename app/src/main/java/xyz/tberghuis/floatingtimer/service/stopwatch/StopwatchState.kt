package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableStateOf
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.OverlayState

class StopwatchState {
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
    // todo pass in scope dependency
    GlobalScope.launch {
      // test flow collect only distinct values
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