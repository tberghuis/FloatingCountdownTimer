package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.service.BubbleProperties

@Composable
fun CountdownCircleView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {
  Box(
    modifier = Modifier
      .size(bubbleProperties.widthDp)
      .padding(bubbleProperties.arcWidth / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    CountdownProgressArc(timeLeftFraction, bubbleProperties.arcWidth, bubbleProperties.haloColor)
    if (isPaused) {
      Icon(
        Icons.Filled.PlayArrow,
        contentDescription = "paused",
        modifier = Modifier.fillMaxSize(),
        tint = Color.LightGray
      )
    }
    TimeDisplay(countdownSeconds, bubbleProperties.fontSize)
  }
}