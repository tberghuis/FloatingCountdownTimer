package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.composables.XxxTrash

@Composable
fun IsDraggingOverlay(overlayState: XxxOverlayState) {
  Column(
    Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    XxxTrash(overlayState)
  }
}