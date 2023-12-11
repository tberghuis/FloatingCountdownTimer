package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.composables.LocalHaloColour

@Composable
fun TmpTimerDisplay(
  vm: TmpVm = viewModel()
) {
  CompositionLocalProvider(LocalHaloColour provides MaterialTheme.colorScheme.primary) {
    CompositionLocalProvider(
      LocalDensity provides Density(
        LocalDensity.current.density,
        1f
      )
    ) {
      Box(
        modifier = Modifier
          .size(TIMER_SIZE_NO_SCALE * (vm.timerSizeScaleFactor + 1))
          .padding(ARC_WIDTH_NO_SCALE / 2),
        contentAlignment = Alignment.Center
      ) {

        Text(
          "00:59", fontSize = TIMER_FONT_SIZE_NO_SCALE * (1.2 * vm.timerSizeScaleFactor + 1),
          style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
        )
      }
    }
  }
}