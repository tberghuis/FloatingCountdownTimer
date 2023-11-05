package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column {
    Text("tmp screen")
    Button(onClick = {
      vm.bindFloatingService()
    }) {
      Text("bind service")
    }
    Button(onClick = {
      vm.addStopwatch()
    }) {
      Text("addStopwatch")
    }

  }
}