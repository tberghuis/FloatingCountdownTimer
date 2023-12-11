package xyz.tberghuis.floatingtimer.common

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun TimeDisplay(totalSeconds: Int, fontSize: TextUnit) {
  val minutes = totalSeconds / 60
  val seconds = totalSeconds % 60
  Text(
    "${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}",
    fontSize = fontSize,
    style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
  )
}

fun formatIntTimerDisplay(t: Int): String {
  return t.toString().padStart(2, '0')
}