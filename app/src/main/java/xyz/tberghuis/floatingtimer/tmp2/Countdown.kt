package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.TimerState
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning

class Countdown(
  service: FloatingService,
  val durationSeconds: Int = 10
) : Bubble(service) {
  var countdownSeconds by mutableStateOf(10)
  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)

  override fun reset() {
    countdownSeconds = durationSeconds
    timerState.value = TimerStatePaused
  }

  override fun onTap() {
    logd("click target onclick")
    when (timerState.value) {
      is TimerStatePaused -> {
        timerState.value = TimerStateRunning
      }

      is TimerStateRunning -> {
        timerState.value = TimerStatePaused
      }

      is TimerStateFinished -> {
        reset()
      }
    }
  }
}