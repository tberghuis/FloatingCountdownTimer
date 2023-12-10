package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("resize timer demo")
    TmpTimerDisplay()
    TmpSliderScale()
  }
}

@Composable
fun TmpSliderScale(
  vm: TmpVm = viewModel()
) {
  Slider(
    value = vm.timerSizeScaleFactor,
    onValueChange = {
      vm.timerSizeScaleFactor = it
    },
    modifier = Modifier,
    valueRange = 0f..1f,
  )
}
