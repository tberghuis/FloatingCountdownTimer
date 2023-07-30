package xyz.tberghuis.floatingtimer.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchOverlay

//@Composable
//fun OverlayContent() {
//  val state = LocalServiceState.current
//  val isCountdownVisible = state.countdownState.overlayState.isVisible.collectAsState()
//  val isStopwatchVisible = state.stopwatchState.overlayState.isVisible.collectAsState()
//
//  if (isCountdownVisible.value == true) {
////    CountdownOverlay(state)
//  }
//  if (isStopwatchVisible.value == true) {
//    StopwatchOverlay()
//  }
//}