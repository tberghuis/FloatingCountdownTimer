package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.service.countdown.TimerState
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused

class Countdown(private val service: FloatingService) : Bubble(service) {
  var durationSeconds: Int = 10
  var countdownSeconds by mutableStateOf<Int>(10)
  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)
  override fun reset() {
    TODO("Not yet implemented")
  }

  override fun onTap() {
    TODO("Not yet implemented")
  }
}