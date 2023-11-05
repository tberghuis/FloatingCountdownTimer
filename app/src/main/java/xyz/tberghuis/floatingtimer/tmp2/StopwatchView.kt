package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState


@Composable
fun StopwatchView(stopwatchState: StopwatchState) {
  Box(
    modifier = Modifier
      .size(TIMER_SIZE_DP.dp)
      .zIndex(1f)
      .background(Color.LightGray),
    contentAlignment = Alignment.Center
  ) {
    Text("${stopwatchState.timeElapsed.value}")
  }
}