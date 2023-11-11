package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.MutableStateFlow

class OverlayState {
  val isVisible = MutableStateFlow<Boolean?>(null)
  var timerOffset by mutableStateOf(IntOffset.Zero)

  var isTimerHoverTrash = false

  fun reset() {
    timerOffset = IntOffset.Zero
    isTimerHoverTrash = false
  }
}