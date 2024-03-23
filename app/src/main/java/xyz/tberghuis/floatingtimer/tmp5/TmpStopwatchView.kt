package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.composables.TimerRectView
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchCircleView
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchView


@Composable
fun TmpStopwatchView(
  stopwatch: TmpStopwatch
) {
  TmpStopwatchView(
    isRunningStateFlow = stopwatch.isRunningStateFlow,
    widthDp = stopwatch.widthDp,
    heightDp = stopwatch.heightDp,
    arcWidth = stopwatch.arcWidth,
    haloColor = stopwatch.haloColor,
    timeElapsed = stopwatch.timeElapsed.intValue,
    fontSize = stopwatch.fontSize,
    timerShape = stopwatch.timerShape,
  )
}

@Composable
fun TmpStopwatchView(
  // when isRunningStateFlow null, showing preview in saved timers card
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  widthDp: Dp,
  heightDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
  timerShape: String
) {
  when (timerShape) {
    "circle" -> {
      StopwatchCircleView(
        isRunningStateFlow = isRunningStateFlow,
        bubbleSizeDp = widthDp,
        arcWidth = arcWidth,
        haloColor = haloColor,
        timeElapsed = timeElapsed,
        fontSize = fontSize
      )
    }

    "rectangle" -> {
      val isPaused = isRunningStateFlow?.collectAsState()?.value?.not() ?: false
      TimerRectView(
        isPaused = isPaused,
        widthDp = widthDp,
        heightDp = heightDp,
        arcWidth = arcWidth,
        haloColor = haloColor,
        timeElapsed = timeElapsed,
        timeLeftFraction = 1f,
        fontSize = fontSize,
      )
    }

    "label" -> {
      TmpTimerLabelView()
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}
