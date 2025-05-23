package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.service.Bubble
import xyz.tberghuis.floatingtimer.service.FloatingService
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

class Stopwatch(
  service: FloatingService,
  bubbleSizeScaleFactor: Float,
  haloColor: Color,
  timerShape: String,
  label: String?,
  isBackgroundTransparent: Boolean,
  savedTimer: SavedTimer? = null,
  start: Boolean = false,
) : Bubble(
  service,
  bubbleSizeScaleFactor,
  haloColor,
  timerShape,
  label,
  isBackgroundTransparent,
  savedTimer
) {
  val timeElapsed = mutableIntStateOf(0)
  val isRunningStateFlow = MutableStateFlow(start)
  private var stopwatchIncrementTask: TimerTask? = null

  // doitwrong
  init {
    service.scope.launch {
      isRunningStateFlow.collect { running ->
        when (running) {
          true -> {
            stopwatchIncrementTask = timerTask {
              timeElapsed.intValue++
            }
            // ignore lint warning as I think would be more accurate than .schedule()
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

  override fun reset() {
    timeElapsed.intValue = 0
    isRunningStateFlow.value = false
    stopwatchIncrementTask?.cancel()
    stopwatchIncrementTask = null
  }

  override fun onTap() {
    isRunningStateFlow.update { isRunning ->
      // only save position on first start of timer
      // not 100% accurate but good enough
      if (timeElapsed.intValue == 0 && !isRunning) {
        saveTimerPositionIfNull()
      }
      !isRunning
    }
  }
}