package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.CountdownOverlay
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchOverlay

@Composable
fun OverlayContent() {
  val state = LocalServiceState.current
  val isCountdownVisible = state.countdownState.overlayState.isVisible.collectAsState()
  val isStopwatchVisible = state.stopwatchState.overlayState.isVisible.collectAsState()

  Box(Modifier.onGloballyPositioned {
    state.screenWidthPx = it.size.width
    state.screenHeightPx = it.size.height
  }) {
    // future.txt correct z-order
    if (isCountdownVisible.value == true) {
      CountdownOverlay(state)
    }
    if (isStopwatchVisible.value == true) {
      StopwatchOverlay()
    }
  }
}