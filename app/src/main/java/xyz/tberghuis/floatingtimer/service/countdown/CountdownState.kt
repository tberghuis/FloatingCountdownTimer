package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.service.OverlayState

class CountdownState {
  val overlayState = OverlayState()

  var durationSeconds: Int = 10
  var countdownSeconds by mutableStateOf<Int>(10)
  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)

  fun resetTimerState(duration: Int = this.durationSeconds) {
    durationSeconds = duration
    countdownSeconds = duration
    timerState.value = TimerStatePaused
  }
}

sealed class TimerState {}

object TimerStateRunning : TimerState()
object TimerStatePaused : TimerState()
object TimerStateFinished : TimerState()
