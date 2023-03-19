package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.min
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.common.OverlayStateFDSFSDF
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchStateGDFGDFG
import xyz.tberghuis.floatingtimer.stopwatch.composables.BorderArc

@Composable
fun StopwatchOverlay(overlayState: OverlayStateFDSFSDF, stopwatchState: StopwatchStateGDFGDFG) {
  val timerSizePx = LocalDensity.current.run { TIMER_SIZE_DP.dp.toPx() }.toInt()

  Box(
    Modifier.onGloballyPositioned {
//      logd("TimerOverlay onGloballyPositioned")
      overlayState.screenWidthPx = it.size.width
      overlayState.screenHeightPx = it.size.height
      val x = min(overlayState.timerOffset.x, overlayState.screenWidthPx - timerSizePx)
      val y = min(overlayState.timerOffset.y, overlayState.screenHeightPx - timerSizePx)
      overlayState.timerOffset = IntOffset(x, y)
    }
  ) {
    Box(
      modifier = Modifier
        .offset {
          overlayState.timerOffset
        }
        .size(TIMER_SIZE_DP.dp)
//        .background(Color.Yellow)
        .padding(PROGRESS_ARC_WIDTH / 2),
      contentAlignment = Alignment.Center
    ) {
      BorderArc(stopwatchState)
      TimeDisplay(stopwatchState.timeElapsed.value)
    }

    if (overlayState.showTrash) {
      Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
//        Trash(overlayState)
      }
    }
  }
}
