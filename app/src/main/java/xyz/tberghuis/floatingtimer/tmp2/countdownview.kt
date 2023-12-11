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
import xyz.tberghuis.floatingtimer.service.countdown.ProgressArc

@Composable
fun TmpCountdownBubble(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  Box(
    modifier = Modifier
      .size(countdown.bubbleSizeDp)
      .padding(countdown.arcWidth / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    ProgressArc(timeLeftFraction, countdown)
    TimeDisplay(countdown.countdownSeconds, countdown.fontSize)
  }
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

