package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.logd

//@Composable
//fun ClickTarget(
//  onClick: () -> Unit
//) {
//  Box(modifier = Modifier
//    .border(BorderStroke(2.dp, Color.Green))
//    .clickable {
//      onClick()
//    }
//  ) {
//    Text("click target")
//  }
//}


@Composable
fun ClickTarget(
  controller: OverlayController,
  overlayState: OverlayState,
  viewHolder: OverlayViewHolder,
  onClick: ()->Unit
) {
  Box(modifier = Modifier
    .pointerInput(Unit) {
      detectDragGestures(onDragStart = {
        logd("clicktarget onDragStart")
        overlayState.showTrash = true
      }, onDrag = { change, dragAmount ->
//                change.consumeAllChanges()
        change.consume()
        val dragAmountIntOffset =
          IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
        val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
        var x = max(_timerOffset.x, 0)
        x = min(x, controller.screenWidthPx - controller.timerSizePx)
        var y = max(_timerOffset.y, 0)
        y = min(y, controller.screenHeightPx - controller.timerSizePx)
        overlayState.timerOffset = IntOffset(x, y)
      }, onDragEnd = {
        logd("onDragEnd")
        overlayState.showTrash = false

        if (overlayState.isTimerHoverTrash) {
          // todo
//          endService()
          return@detectDragGestures
        }

        viewHolder.params.x = overlayState.timerOffset.x
        viewHolder.params.y = overlayState.timerOffset.y
        logd("onDragEnd x ${overlayState.timerOffset.x}")
        controller.windowManager.updateViewLayout(viewHolder.view, viewHolder.params)
      })
    }
    .clickable {
      onClick()
    }) {
//        Text("click target")
  }

}


