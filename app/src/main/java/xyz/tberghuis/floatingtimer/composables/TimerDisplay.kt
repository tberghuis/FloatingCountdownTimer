package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.OverlayStateHolder.countdownSeconds

@Composable
fun TimerDisplay() {
// doitwrong
  // should i use derivedStateOf ???
  val minutes = countdownSeconds / 60
  val seconds = countdownSeconds % 60
  Text("${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}")
}

fun formatIntTimerDisplay(t: Int): String {
  return t.toString().padStart(2, '0')
}