package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.composables.TimerText
import xyz.tberghuis.floatingtimer.composables.formatIntTimerDisplay

@Composable
fun TmpTimeDisplay(
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