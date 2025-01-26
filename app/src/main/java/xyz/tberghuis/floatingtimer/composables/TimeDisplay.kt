package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun TimeDisplay(
  totalSeconds: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  val hours = totalSeconds / (3600)
  val minutes = (totalSeconds % (3600)) / 60
  val seconds = totalSeconds % 60
  var text = "${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}"
  if (hours > 0) {
    text = "${formatIntTimerDisplay(hours)}:$text"
  }
  TimerText(
    text,
    fontSize,
    isBackgroundTransparent
  )
}