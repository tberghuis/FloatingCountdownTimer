package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import xyz.tberghuis.floatingtimer.logd

@Composable
fun ClickTarget(
  bubbleContent: @Composable () -> Unit,
  onDoubleTap: () -> Unit,
  onTap: () -> Unit
) {

  Box(modifier = Modifier
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
    bubbleContent()
  }
}




