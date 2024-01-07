package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpNavigateResult(
  vm: TmpNavigateResultVm = viewModel()
) {
  val navController = LocalNavController.current

  val customColor by vm.customColor.collectAsState()

  Column {
    Text("tmp nav result ${vm.fdfsd}")
    Text("custom color: ${customColor}")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

class TmpNavigateResultVm(private val state: SavedStateHandle) : ViewModel() {
  val fdfsd = "fdsfs"

  val customColor = state.getStateFlow("custom_color", "defaultValue")

  init {
    logd("TmpNavigateResultVm init")
  }


}