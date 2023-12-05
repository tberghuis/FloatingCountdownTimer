package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.countdown.Countdown
import xyz.tberghuis.floatingtimer.service.countdown.ProgressArc

@Composable
fun CountdownView(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  Box(
    modifier = Modifier
      .size(TIMER_SIZE_DP.dp)
      .padding(PROGRESS_ARC_WIDTH / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    ProgressArc(timeLeftFraction)
    TimeDisplay(countdown.countdownSeconds)
  }
}