package xyz.tberghuis.floatingtimer.stopwatch.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.common.OverlayState
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchExit
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchServiceHolder
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchState
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun StopwatchClickTarget(overlayState: OverlayState) {

  val timerSizePx = LocalDensity.current.run { TIMER_SIZE_DP.dp.toPx() }.toInt()

  Box(
    modifier = Modifier
//      .background(Color.Red)
      .pointerInput(Unit) {
        detectDragGestures(onDragStart = {
          logd("clicktarget onDragStart")
          overlayState.showTrash = true
        },
          onDrag = { change, dragAmount ->
            change.consumeAllChanges()
            val dragAmountIntOffset =
              IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
            val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
            var x = max(_timerOffset.x, 0)
            x = min(x, overlayState.screenWidthPx - timerSizePx)
            var y = max(_timerOffset.y, 0)
            y = min(y, overlayState.screenHeightPx - timerSizePx)
            overlayState.timerOffset = IntOffset(x, y)
          },
          onDragEnd = {
            logd("onDragEnd")
            overlayState.showTrash = false

            if (overlayState.isTimerHoverTrash) {
              stopwatchExit()
              return@detectDragGestures
            }

            // doitwrong
            val cto = stopwatchServiceHolder.stopwatchOverlayComponent.clickTargetOverlay
            val wm = stopwatchServiceHolder.stopwatchOverlayComponent.windowManager

            cto.params.x = overlayState.timerOffset.x
            cto.params.y = overlayState.timerOffset.y
            logd("onDragEnd x ${overlayState.timerOffset.x}")
            wm.updateViewLayout(cto.view, cto.params)
          }
        )
      }
      .clickable {
        onClickStopwatchClickTarget()
      }

  ) {
//    Text("hello click target")
  }

}


// todo move into another file
fun onClickStopwatchClickTarget() {
  logd("click target start pause")
  when (stopwatchState.running.value) {
    false -> {
      stopwatchState.running.value = true
      Timer().scheduleAtFixedRate(timerTask {
//        logd("timertask")
        if (stopwatchState.running.value) {
          stopwatchState.timeElapsed.value++
        } else {
          cancel()
        }
      }, 1000, 1000)
    }
    true -> {
      stopwatchState.running.value = false
    }
  }
}