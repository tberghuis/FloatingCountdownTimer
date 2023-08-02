package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.torrydo.screenez.ScreenEz
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import kotlin.math.min
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.composables.Trash
import xyz.tberghuis.floatingtimer.service.ServiceState

@Composable
fun CountdownBubble(modifier: Modifier, countdownState: CountdownState) {
  val timeLeftFraction = countdownState.countdownSeconds / countdownState.durationSeconds.toFloat()
  Box(
    modifier = Modifier
      .then(modifier)
      .size(TIMER_SIZE_DP.dp)
      .padding(PROGRESS_ARC_WIDTH / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    ProgressArc(timeLeftFraction)
    TimeDisplay(countdownState.countdownSeconds)
  }
}