package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpChangeColor(
  vm: TmpChangeColorVm = viewModel()
) {
  val navController = LocalNavController.current

  val homeVm: TmpNavigateResultVm = viewModel(navController.previousBackStackEntry!!)


  Column {
    Text("tmp change color")
    Button(onClick = {

      homeVm.state["custom_color"] = "will it blend"
      navController.popBackStack()

    }) {
      Text("button")
    }
  }
}

class TmpChangeColorVm(private val state: SavedStateHandle) : ViewModel() {

}