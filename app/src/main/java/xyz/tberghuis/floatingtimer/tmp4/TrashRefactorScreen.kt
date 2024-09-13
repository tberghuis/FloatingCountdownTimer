package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TrashRefactorScreen(
  vm: Tmp4Vm = viewModel()
) {
  Column() {
    Text("TrashRefactorScreen")
    Button(onClick = {
      logd("${vm.willitblend}")
    }) {
      Text("button")
    }
  }
}

@Preview
@Composable
fun TrashRefactorScreenPreview() {
  TrashRefactorScreen()
}