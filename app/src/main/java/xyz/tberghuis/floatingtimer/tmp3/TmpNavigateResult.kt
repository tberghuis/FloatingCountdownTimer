package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun TmpNavigateResult(
  vm: TmpNavigateResultVm = viewModel()
) {
  val navController = LocalNavController.current

  Column {
    Text("tmp nav result ${vm.fdfsd}")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

class TmpNavigateResultVm : ViewModel() {
  val fdfsd = "fdsfs"
}