package xyz.tberghuis.floatingtimer

import android.app.PendingIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.OverlayStateHolder.countdownSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.durationSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerState

// TODO refactor to class
object OverlayStateHolder {
  var durationSeconds: Int = 10
  var countdownSeconds by mutableStateOf<Int>(10)

  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)

  // don't really need because i can cancel with requestCode???
  var pendingAlarm: PendingIntent? = null
}

fun resetTimerState(duration: Int) {
  durationSeconds = duration
  countdownSeconds = duration
  timerState.value = TimerStatePaused
}


sealed class TimerState {
}

object TimerStateRunning : TimerState()
object TimerStatePaused : TimerState()
object TimerStateFinished : TimerState()