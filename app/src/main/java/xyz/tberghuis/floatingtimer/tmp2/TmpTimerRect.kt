package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.tmp.TmpVm
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpTimerRect(
  vm: TmpVm = viewModel()
) {
  // represents window.addView size....


  Box(
    modifier = Modifier
      .padding(5.dp),
    contentAlignment = Alignment.Center,
  ) {

    Surface(
      modifier = Modifier,
      shape = RoundedCornerShape(10.dp),
      shadowElevation = 5.dp,
    ) {
      Column(
        modifier = Modifier
          .size(vm.settingsTimerPreviewVmc.bubbleSizeDp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text("00:59")

        CountdownProgressLine(
          0.5f,
          vm.settingsTimerPreviewVmc.arcWidth,
          vm.settingsTimerPreviewVmc.haloColor
        )


      }
    }
  }
}