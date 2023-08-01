package xyz.tberghuis.floatingtimer.tmp.dragdrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.tmp.LocalMaccasOverlayController
import xyz.tberghuis.floatingtimer.tmp.MaccasOverlayContent
import xyz.tberghuis.floatingtimer.tmp.MaccasPassInComposable


@Composable
fun DDBubble() {
  val controller = LocalMaccasOverlayController.current
  val isDragging = controller.bubbleState.isDragging.collectAsState()

  Box() {
    // pass this in
    val modifier = Modifier.fillMaxSize()
    MaccasOverlayContent(modifier) {
      MaccasPassInComposable()
    }
  }
}