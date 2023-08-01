package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.composables.Trash
import xyz.tberghuis.floatingtimer.service.LocalServiceState
import xyz.tberghuis.floatingtimer.service.OverlayState

@Composable
fun IsDraggingOverlay(overlayState: OverlayState) {
//  val serviceState = LocalServiceState.current
//  val stopwatchState = serviceState.stopwatchState
//  val overlayState = stopwatchState.overlayState



  Column(
    Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Trash(overlayState)
  }
}
