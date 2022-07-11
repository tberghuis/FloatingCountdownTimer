package xyz.tberghuis.floatingtimer.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset

class OverlayState {
  var timerOffset by mutableStateOf(IntOffset.Zero)
  var showTrash by mutableStateOf(false)
  var isTimerHoverTrash = false

  // i could probably move these top level
  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE
}

val stopwatchOverlayState = OverlayState()
val countdownOverlayState = OverlayState()