package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import xyz.tberghuis.floatingtimer.service.countdown.CountdownOverlay

@Composable
fun OverlayContent() {
  val controller = LocalOverlayController.current
  val isCountdownVisible =
    controller.countdownState.overlayState.isVisible.collectAsState()

  Box(Modifier.onGloballyPositioned {

  }) {
    if (isCountdownVisible.value == true) {
      CountdownOverlay(controller)
    }
  }
}