package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

class TmpTrashState {
  val isBubbleDragging = MutableStateFlow(false)
  //  val isTimerHover = MutableStateFlow(false)
  val isTimerHover = mutableStateOf(false)
}