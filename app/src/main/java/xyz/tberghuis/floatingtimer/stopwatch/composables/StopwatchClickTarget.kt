package xyz.tberghuis.floatingtimer.stopwatch.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.logd
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchStateGDFGDFG

//@Composable
//fun StopwatchClickTargetCDSCDS(overlayState: OverlayStateFDSFSDF, stopwatchState: StopwatchStateGDFGDFG) {
//  val timerSizePx = LocalDensity.current.run { TIMER_SIZE_DP.dp.toPx() }.toInt()
//  val stopwatchOverlayComponent = LocalStopwatchOverlayComponent.current
//
//  Box(
//    modifier = Modifier
//      .pointerInput(Unit) {
//        detectDragGestures(onDragStart = {
//          logd("clicktarget onDragStart")
//          overlayState.showTrash = true
//        },
//          onDrag = { change, dragAmount ->
//            change.consume()
//            val dragAmountIntOffset =
//              IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
//            val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
//            var x = max(_timerOffset.x, 0)
//            x = min(x, overlayState.screenWidthPx - timerSizePx)
//            var y = max(_timerOffset.y, 0)
//            y = min(y, overlayState.screenHeightPx - timerSizePx)
//            overlayState.timerOffset = IntOffset(x, y)
//          },
//          onDragEnd = {
//            logd("onDragEnd")
//            overlayState.showTrash = false
//
//            if (overlayState.isTimerHoverTrash) {
//              stopwatchExit(stopwatchOverlayComponent, stopwatchState)
//              return@detectDragGestures
//            }
//
//            val cto = stopwatchOverlayComponent.clickTargetOverlay
//            val wm = stopwatchOverlayComponent.windowManager
//
//            cto.params.x = overlayState.timerOffset.x
//            cto.params.y = overlayState.timerOffset.y
//            logd("onDragEnd x ${overlayState.timerOffset.x}")
//            wm.updateViewLayout(cto.view, cto.params)
//          }
//        )
//      }
//      .clickable {
//        onClickStopwatchClickTarget(stopwatchState)
//      }
//
//  ) {
////    Text("hello click target")
//  }
//
//}

