package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.tmp4.TmpTimeDisplay

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

@Composable
fun OutlinedTextWithShadow(
  text: String,
  fontSize: TextUnit,
) {
  val textStyleFill = LocalTextStyle.current.copy(
    fontSize = fontSize,
    color = Color.White,
    fontFeatureSettings = "tnum",
    drawStyle = Fill
  )
  val textStyleOutline = textStyleFill.copy(
    color = Color.Black,
    drawStyle = Stroke(
      miter = 10f,
      width = 5f,
      join = StrokeJoin.Round
    )
  )
  Box {
    Text(
      text = text,
      fontFamily = FontFamily.Default,
      maxLines = 1,
      style = textStyleOutline,
    )
    Text(
      text = text,
      fontFamily = FontFamily.Default,
      maxLines = 1,
      style = textStyleFill,
    )
  }
}

@Composable
fun TimeDisplay(
  totalSeconds: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  TmpTimeDisplay(
    totalSeconds,
    fontSize,
    isBackgroundTransparent
  )
}

@Composable
fun XxxTimeDisplay(
  totalSeconds: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  val minutes = totalSeconds / 60
  val seconds = totalSeconds % 60
  val text = "${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}"
  TimerText(
    text,
    fontSize,
    isBackgroundTransparent
  )
}