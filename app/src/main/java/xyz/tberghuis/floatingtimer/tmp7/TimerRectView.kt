package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.common.TimeDisplay

@Composable
fun TimerRectView(
  isPaused: Boolean,
  widthDp: Dp,
  heightDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  timeLeftFraction: Float,
  fontSize: TextUnit,
) {
  Box(
    modifier = Modifier
      .size(widthDp, heightDp)
      .padding(5.dp)
      .graphicsLayer(
        shadowElevation = with(LocalDensity.current) { 5.dp.toPx() },
        shape = RoundedCornerShape(10.dp),
        clip = true
      ),
    contentAlignment = Alignment.Center,
  ) {

    Box(
      modifier = Modifier
        .background(Color.White)
        .fillMaxSize(),
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
          .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        TimeDisplay(timeElapsed, fontSize)
        Box(
          modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
          ),
          contentAlignment = Alignment.TopStart,
        ) {
          CountdownProgressLine(
            timeLeftFraction,
            arcWidth,
            haloColor
          )
        }
      }
    }
  }
}

@Composable
fun CountdownProgressLine(timeLeftFraction: Float, arcWidth: Dp, haloColor: Color) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(arcWidth)
      .background(haloColor.copy(alpha = .1f))
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(timeLeftFraction)
        .height(arcWidth)
        .background(haloColor)
    )
  }
}