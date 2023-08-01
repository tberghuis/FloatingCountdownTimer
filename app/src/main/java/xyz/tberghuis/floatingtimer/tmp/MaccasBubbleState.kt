package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.MutableStateFlow

class MaccasBubbleState {
  var offsetPx by mutableStateOf(IntOffset.Zero)
  val isDragging = MutableStateFlow<Boolean?>(null)
}
