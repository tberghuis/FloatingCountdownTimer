package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun MaccasBubble() {
  val controller = LocalMaccasOverlayController.current
  val isDragging = controller.bubbleState.isDragging.collectAsState()

  Box(modifier = Modifier
    .pointerInput(Unit) {
      detectDragGestures(onDragStart = {
        logd("clicktarget onDragStart")
      }, onDrag = { change, dragAmount ->
        controller.bubbleState.isDragging.value = true
        change.consume()
        val dragAmountIntOffset = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
        val _timerOffset = controller.bubbleState.offset + dragAmountIntOffset
        var x = max(_timerOffset.x, 0)
        var y = max(_timerOffset.y, 0)
        controller.bubbleState.offset = IntOffset(x, y)
      }, onDragEnd = {
        logd("onDragEnd")

        controller.bubbleParams.x = controller.bubbleState.offset.x
        controller.bubbleParams.y = controller.bubbleState.offset.y
        controller.windowManager.updateViewLayout(controller.bubbleView, controller.bubbleParams)
        controller.bubbleState.isDragging.value = false
      })
    }
  ) {
    if (isDragging.value != true) {
      // pass this in
      val modifier = Modifier.fillMaxSize()
      MaccasOverlayContent(modifier) {
        MaccasPassInComposable()
      }
    }
  }
}

@Composable
fun MaccasOverlayContent(modifier: Modifier, content: @Composable () -> Unit) {
  Box(
    modifier = Modifier
      .then(modifier)
      .background(Color.Gray)
  ) {
//    Text("0:00")
    content()
  }
}

@Composable
fun MaccasPassInComposable() {
  Text("0:00")
}