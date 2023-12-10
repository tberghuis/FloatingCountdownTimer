package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    TmpTimer()
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

@Composable
fun TmpTimer() {
  Box(
    modifier = Modifier.border(1.dp, Color.Black),
  ) {
    Text("00:59", fontSize = 20.dp)
  }
}
