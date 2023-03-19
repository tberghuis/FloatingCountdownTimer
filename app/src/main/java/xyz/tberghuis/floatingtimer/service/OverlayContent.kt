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
  val state = LocalServiceState.current

//  val controller = LocalOverlayController.current
  val isCountdownVisible = state.countdownState.overlayState.isVisible.collectAsState()

  // todo refactor timers to share code ...
  // move onGloballyPositioned code here
  Box(Modifier.onGloballyPositioned {
    state.screenWidthPx = it.size.width
    state.screenHeightPx = it.size.height
  }) {
    if (isCountdownVisible.value == true) {
      CountdownOverlay(state)
    }
  }
}