package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.tmp4.OutlinedTextWithShadow

fun formatIntTimerDisplay(t: Int): String {
  return t.toString().padStart(2, '0')
}

@Composable
fun TimerText(
  text: String,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  CompositionLocalProvider(
    LocalDensity provides Density(LocalDensity.current.density, 1f)
  ) {
    if (isBackgroundTransparent) {
      OutlinedTextWithShadow(text, fontSize)
    } else {
      Text(
        text,
        fontSize = fontSize,
        fontFamily = FontFamily.Default,
        maxLines = 1,
        style = LocalTextStyle.current.copy(
          color = Color.Black,
          fontFeatureSettings = "tnum"
        ),
      )
    }
  }
}