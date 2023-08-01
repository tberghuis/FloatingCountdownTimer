package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_PX
import xyz.tberghuis.floatingtimer.logd

// doitwrong
@Composable
fun TmpClickTarget(
  serviceState: ServiceState,
  controller: OverlayController,
  overlayState: OverlayState,
  viewHolder: OverlayViewHolder,
  bubbleContent: @Composable () -> Unit,
  onDropOnTrash: () -> Unit,
  onDoubleTap: () -> Unit,
  onTap: () -> Unit
) {
  val isDragging = overlayState.isDragging.collectAsState()

  Box(modifier = Modifier
    .pointerInput(Unit) {
      detectDragGestures(onDragStart = {
        logd("clicktarget onDragStart")
        overlayState.isDragging.value = true
      }, onDrag = { change, dragAmount ->
        change.consume()
        val dragAmountIntOffset = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
        val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
        var x = max(_timerOffset.x, 0)
        x = min(x, serviceState.screenWidthPx - TIMER_SIZE_PX)
        var y = max(_timerOffset.y, 0)
        y = min(y, serviceState.screenHeightPx - TIMER_SIZE_PX)
        overlayState.timerOffset = IntOffset(x, y)
      }, onDragEnd = {
        logd("onDragEnd")
        overlayState.isDragging.value = false
        if (overlayState.isTimerHoverTrash) {
          onDropOnTrash()
          return@detectDragGestures
        }
        viewHolder.params.x = overlayState.timerOffset.x
        viewHolder.params.y = overlayState.timerOffset.y
        logd("onDragEnd x ${overlayState.timerOffset.x}")
        controller.windowManager.updateViewLayout(viewHolder.view, viewHolder.params)
      })
    }
    .pointerInput(Unit) {
      detectTapGestures(
        onDoubleTap = {
          logd("onDoubleTap")
          onDoubleTap()
        },
        onTap = {
          logd("onTap")
          onTap()
        }
      )
    }
  ) {
    if (isDragging.value != true) {
      bubbleContent()
    }
  }
}


@Composable
fun ClickTarget(
  serviceState: ServiceState,
  controller: OverlayController,
  overlayState: OverlayState,
  viewHolder: OverlayViewHolder,
  bubbleContent: @Composable () -> Unit,
  onDropOnTrash: () -> Unit,
  onDoubleTap: () -> Unit,
  onTap: () -> Unit
) {
  val isDragging = overlayState.isDragging.collectAsState()

  Box(modifier = Modifier
    .pointerInput(Unit) {
      detectDragGestures(onDragStart = {
        logd("clicktarget onDragStart")
        overlayState.isDragging.value = true
      }, onDrag = { change, dragAmount ->
        change.consume()
        val dragAmountIntOffset = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
        val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
        var x = max(_timerOffset.x, 0)
        x = min(x, serviceState.screenWidthPx - TIMER_SIZE_PX)
        var y = max(_timerOffset.y, 0)
        y = min(y, serviceState.screenHeightPx - TIMER_SIZE_PX)
        overlayState.timerOffset = IntOffset(x, y)
      }, onDragEnd = {
        logd("onDragEnd")
        overlayState.isDragging.value = false
        if (overlayState.isTimerHoverTrash) {
          onDropOnTrash()
          return@detectDragGestures
        }
        viewHolder.params.x = overlayState.timerOffset.x
        viewHolder.params.y = overlayState.timerOffset.y
        logd("onDragEnd x ${overlayState.timerOffset.x}")
        controller.windowManager.updateViewLayout(viewHolder.view, viewHolder.params)
      })
    }
    .pointerInput(Unit) {
      detectTapGestures(
        onDoubleTap = {
          logd("onDoubleTap")
          onDoubleTap()
        },
        onTap = {
          logd("onTap")
          onTap()
        }
      )
    }
  ) {
    if (isDragging.value != true) {
      bubbleContent()
    }
  }
}




