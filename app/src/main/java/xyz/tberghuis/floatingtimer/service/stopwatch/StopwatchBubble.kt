package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.Bubble
import xyz.tberghuis.floatingtimer.service.FloatingService
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

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
  // this is written to when timer started or resumed
  // no need to make thread safe
  var virtualStartTimestamp = -1L

  val timeElapsed = mutableIntStateOf(0)
  val isRunningStateFlow = MutableStateFlow(start)
  private var stopwatchIncrementTask: TimerTask? = null

  // doitwrong
  init {
    service.scope.launch {
      isRunningStateFlow.collect { running ->
        when (running) {
          true -> {
// stuff the new api for now
//            val markNow = TimeSource.Monotonic.markNow()
            virtualStartTimestamp = System.currentTimeMillis() - (timeElapsed.intValue * 1000)
            logd("virtualStartTimestamp $virtualStartTimestamp")
            stopwatchIncrementTask = timerTask {
//              timeElapsed.intValue++
              val elapsedMillis = System.currentTimeMillis() - virtualStartTimestamp
              timeElapsed.intValue = (elapsedMillis / 1000.0).roundToInt()
            }
            // lint warning
//            Timer().scheduleAtFixedRate(stopwatchIncrementTask, 1000, 1000)
            Timer().schedule(stopwatchIncrementTask, 1000, 1000)
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