package xyz.tberghuis.floatingtimer.service

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.MutableStateFlow


class OverlayState {
  val isVisible = MutableStateFlow<Boolean?>(null)
  val offset = mutableStateOf(IntOffset.Zero)
}