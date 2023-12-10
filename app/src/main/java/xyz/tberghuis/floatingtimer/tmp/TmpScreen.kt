package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column {
    Text("resize timer demo")
    TmpSlider()
  }
}


@Composable
fun TmpSlider(
  vm: TmpVm = viewModel()
) {
  Slider(
    value = vm.timerSizeFactor,
    onValueChange = {
      vm.timerSizeFactor = it
    },
    modifier = Modifier,
    valueRange = 1f..2f,
  )
}
