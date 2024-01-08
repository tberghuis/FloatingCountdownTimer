package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpChangeColor(
  vm: TmpChangeColorVm = viewModel()
) {
  Column {
    Text("change color")
    Text("timerType ${vm.timerType}")
  }
}

class TmpChangeColorVm(savedStateHandle: SavedStateHandle) : ViewModel() {
  // when null it means update user prefs, for default halo_colour
  val timerType: String? = savedStateHandle["timerType"]
}