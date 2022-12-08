package xyz.tberghuis.floatingtimer.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TimeDisplay(totalSeconds: Int) {
  val minutes = totalSeconds / 60
  val seconds = totalSeconds % 60
  Text("${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}")
}

fun formatIntTimerDisplay(t: Int): String {
  return t.toString().padStart(2, '0')
}