package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.composables.CountdownProgressLine
import xyz.tberghuis.floatingtimer.composables.TimeDisplay
import xyz.tberghuis.floatingtimer.composables.TimerText

@Composable
fun TmpTimerRectView(
  isPaused: Boolean,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  timeLeftFraction: Float,
  fontSize: TextUnit,
  label: String?,
  isBackgroundTransparent: Boolean,
) {
  @Suppress("NAME_SHADOWING") val label = if (label == "") null else label

  val bubbleModifier = if (isBackgroundTransparent)
    Modifier
  else
    Modifier
      .padding(5.dp)
      .graphicsLayer(
        shadowElevation = with(LocalDensity.current) { 5.dp.toPx() },
        shape = RoundedCornerShape(10.dp),
        clip = true
      )
      .background(Color.White)

  Box(
    modifier = Modifier
  ) {
    Box(
      modifier = Modifier
        .height(IntrinsicSize.Min)
        .width(IntrinsicSize.Min)
        .then(bubbleModifier),
      contentAlignment = Alignment.Center,
    ) {
      if (isPaused) {
        Icon(
          Icons.Filled.PlayArrow,
          contentDescription = "paused",
          modifier = Modifier.fillMaxSize(),
          tint = Color.LightGray
        )
      }
      Column(
        modifier = Modifier
          .width(IntrinsicSize.Max)
          .padding(5.dp)
      ) {
        Row(
          modifier = Modifier,
        ) {
          label?.let {
            TimerText("$label - ", fontSize = fontSize, isBackgroundTransparent)
          }
          TimeDisplay(timeElapsed, fontSize, isBackgroundTransparent)
        }
        CountdownProgressLine(
          timeLeftFraction,
          arcWidth,
          haloColor
        )
      }
    }
  }
}

@Preview
@Composable
fun TmpTimerRectViewDemo() {
  val fontSize = TextUnit.Unspecified
  val isBackgroundTransparent = false
  TmpTimerRectViewDemo2(fontSize, isBackgroundTransparent)
}


@Composable
fun TmpTimerRectViewDemo2(
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean,
) {
  Row(
    modifier = Modifier
      .background(
        Color.Blue.copy(alpha = .2f)
      )
//      .widthIn(max = 100.dp),
  ) {
    Box(modifier = Modifier.weight(1f)) {
      TimerText("label1fdsfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfdsfsdfsd232434234", fontSize, isBackgroundTransparent)
    }
    Box(modifier = Modifier) {
      TimerText(" - 00:59", fontSize, isBackgroundTransparent)
    }
  }
}