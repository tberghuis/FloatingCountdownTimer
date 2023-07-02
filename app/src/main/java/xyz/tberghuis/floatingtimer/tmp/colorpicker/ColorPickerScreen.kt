package xyz.tberghuis.floatingtimer.tmp.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ColorPickerScreen(
  vm: ColorPickerScreenVm = viewModel()
) {
  Column {
    Text("color picker screen ${vm.dsfds}")
  }
}