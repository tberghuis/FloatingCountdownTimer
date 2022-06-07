package xyz.tberghuis.floatingtimer

import android.app.PendingIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.OverlayStateHolder.countdownSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.durationSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerState

object OverlayStateHolder {
  var timerOffset by mutableStateOf(IntOffset.Zero)
  var showTrash by mutableStateOf(false)
  var isTimerHoverTrash = false

  // this is a hack
  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE

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