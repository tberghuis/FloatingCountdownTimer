package xyz.tberghuis.floatingtimer.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.composables.formatIntTimerDisplay

@Composable
fun TimeDisplay(totalSeconds: Int) {
  val minutes = totalSeconds / 60
  val seconds = totalSeconds % 60
  Text("${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}")
}