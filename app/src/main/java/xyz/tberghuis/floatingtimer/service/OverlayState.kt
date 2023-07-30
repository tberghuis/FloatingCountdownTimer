package xyz.tberghuis.floatingtimer.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.MutableStateFlow

class OverlayState {
  val isVisible = MutableStateFlow<Boolean?>(null)
  var timerOffset by mutableStateOf(IntOffset.Zero)

  //  var showTrash by mutableStateOf(false)
  val isDragging = MutableStateFlow<Boolean?>(null)

  var isTimerHoverTrash = false

  fun reset() {
    timerOffset = IntOffset.Zero
//    showTrash = false
    isDragging.value = null
    isTimerHoverTrash = false
  }
}