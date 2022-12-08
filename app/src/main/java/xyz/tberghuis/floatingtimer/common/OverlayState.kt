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
  // this is a hack
  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE


  fun reset(){
    timerOffset = IntOffset.Zero
    showTrash = false
    isTimerHoverTrash = false
  }

}

// todo initialise in Service
// restore from shared preferences
val countdownOverlayState = OverlayState()
//val stopwatchOverlayState = OverlayState()