package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.tmp5.BubbleProperties

@Composable
fun TmpCountdownView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {
// todo when timerShape
  CountdownCircleView(
    bubbleProperties = bubbleProperties,
    timeLeftFraction = timeLeftFraction,
    countdownSeconds = countdownSeconds,
    isPaused = isPaused
  )
}
