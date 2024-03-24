package xyz.tberghuis.floatingtimer.tmp5

import android.content.Context
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.composables.TimerRectView
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchCircleView
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchView
import xyz.tberghuis.floatingtimer.tmp4.LocalTimerViewHolder


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
  val isPaused = isRunningStateFlow?.collectAsState()?.value?.not() ?: false

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
      val tvh = LocalTimerViewHolder.current
      val windowManager = tvh.service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
      TimerLabelView(
        isPaused,
        arcWidth,
        haloColor,
        timeElapsed,
        1f,
        fontSize,
        "tmp label - "
      ) {
        logd("runOnceOnGloballyPositioned ${it}")
        tvh.params.width = it.width
        tvh.params.height = it.height
        windowManager.updateViewLayout(tvh.view, tvh.params)
      }
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}
