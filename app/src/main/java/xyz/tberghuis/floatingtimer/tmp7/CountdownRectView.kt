package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.tmp5.BubbleProperties


@Composable
fun CountdownRectView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {
  TimerRectView(
    isPaused = isPaused,
    widthDp = bubbleProperties.widthDp,
    heightDp = bubbleProperties.heightDp,
    arcWidth = bubbleProperties.arcWidth,
    haloColor = bubbleProperties.haloColor,
    timeElapsed = countdownSeconds,
    timeLeftFraction = timeLeftFraction,
    fontSize = bubbleProperties.fontSize,
  )
}