package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.common.TimeDisplay

@Composable
fun CountdownView(countdown: Countdown) {
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