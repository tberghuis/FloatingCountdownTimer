package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.composables.TimerRectView

@Composable
fun StopwatchView(
  stopwatch: Stopwatch
) {
  StopwatchView(
    isRunningStateFlow = stopwatch.isRunningStateFlow,
    arcWidth = stopwatch.arcWidth,
    haloColor = stopwatch.haloColor,
    timeElapsed = stopwatch.timeElapsed.intValue,
    fontSize = stopwatch.fontSize,
    timerShape = stopwatch.timerShape,
    label = stopwatch.label,
    isBackgroundTransparent = stopwatch.isBackgroundTransparent,
    paddingTimerDisplay = stopwatch.paddingTimerDisplay
  )
}

@Composable
fun StopwatchView(
  // when isRunningStateFlow null, showing preview in saved timers card
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
  timerShape: String,
  label: String?,
  isBackgroundTransparent: Boolean,
  paddingTimerDisplay: Dp
) {
  val isPaused = isRunningStateFlow?.collectAsState()?.value?.not() ?: false

  when (timerShape) {
    "circle" -> {
      StopwatchCircleView(
        isRunningStateFlow = isRunningStateFlow,
        arcWidth = arcWidth,
        haloColor = haloColor,
        timeElapsed = timeElapsed,
        fontSize = fontSize,
        isBackgroundTransparent = isBackgroundTransparent,
        paddingTimerDisplay = paddingTimerDisplay
      )
    }

    "label", "rectangle" -> {
      TimerRectView(
        isPaused,
        arcWidth,
        haloColor,
        timeElapsed,
        1f,
        fontSize,
        if (timerShape == "label") label else null,
        isBackgroundTransparent,
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}