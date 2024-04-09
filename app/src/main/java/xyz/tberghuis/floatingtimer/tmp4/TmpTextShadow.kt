package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.composables.TimerText
import xyz.tberghuis.floatingtimer.composables.formatIntTimerDisplay

@Preview
@Composable
fun TmpTextShadow() {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Box {
      Box(
        Modifier
          .background(Color.Green)
          .size(200.dp)
      )
      Column {
        TmpTimeDisplay(59, 16.sp, true)
        // 35.2.sp
        TmpTimeDisplay(59, TIMER_FONT_SIZE_NO_SCALE * (1.2 * 1f + 1), true)
      }
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
fun TmpTimeDisplay(
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