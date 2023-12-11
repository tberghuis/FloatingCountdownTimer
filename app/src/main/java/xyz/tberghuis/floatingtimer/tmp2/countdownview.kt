package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.countdown.Countdown

// todo replace CountdownView with this
@Composable
fun TmpCountdownBubble(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  TmpCountdownBubbleDisplay(countdown, timeLeftFraction, countdown.countdownSeconds)
}

// need better naming conventions
@Composable
fun TmpCountdownBubbleDisplay(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int
) {
  Box(
    modifier = Modifier
      .size(bubbleProperties.bubbleSizeDp)
      .padding(bubbleProperties.arcWidth / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    TmpProgressArc(timeLeftFraction, bubbleProperties.arcWidth)
    TimeDisplay(countdownSeconds, bubbleProperties.fontSize)
  }
}