package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay
import xyz.tberghuis.floatingtimer.tmp2.TmpTimerRect
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {


  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    Row {
      CountdownViewDisplay(vm.settingsTimerPreviewVmc, 0.6f, 59, false)
      TmpTimerRect()
    }


  }
}