package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MaccasFullscreen() {
  val controller = LocalMaccasOverlayController.current

  Box(
    modifier = Modifier
  ) {
    val modifier = Modifier
      .offset {
        controller.bubbleState.offset
      }
      .size(MC.OVERLAY_SIZE_DP.dp)
    MaccasOverlayContent(modifier)
  }
}