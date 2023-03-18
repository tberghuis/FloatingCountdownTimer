package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun OverlayContent() {
  val isCountdownVisible =
    LocalOverlayController.current.countdownState.overlayState.isVisible.collectAsState()

  Box(Modifier.onGloballyPositioned {

  }) {
    if (isCountdownVisible.value == true) {
      Text("hello countdown")
    }
  }
}