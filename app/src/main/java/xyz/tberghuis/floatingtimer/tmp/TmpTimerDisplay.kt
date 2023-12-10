package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP

@Composable
fun TmpTimerDisplay(
  vm: TmpVm = viewModel()
) {
  CompositionLocalProvider(
    LocalDensity provides Density(
      LocalDensity.current.density,
      1f
    )
  ) {
    Box(
      modifier = Modifier
        .size(TIMER_SIZE_DP.dp * (vm.timerSizeScaleFactor + 1))
        .padding(PROGRESS_ARC_WIDTH / 2)
        .border(1.dp, Color.Black),
      contentAlignment = Alignment.Center
    ) {
      //
      Text("00:59", fontSize = 16.sp * (1.5 * vm.timerSizeScaleFactor + 1))
    }
  }
}

