package xyz.tberghuis.floatingtimer.common

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit

@Composable
fun TimeDisplay(totalSeconds: Int, fontSize: TextUnit, fontColor: Color) {
  val minutes = totalSeconds / 60
  val seconds = totalSeconds % 60

  CompositionLocalProvider(
    LocalDensity provides Density(LocalDensity.current.density, 1f)
  ) {
    Text(
      "${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}",
      fontSize = fontSize,
      fontFamily = FontFamily.Default,
      style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
      color = fontColor
    )
  }
}

fun formatIntTimerDisplay(t: Int): String {
  return t.toString().padStart(2, '0')
}