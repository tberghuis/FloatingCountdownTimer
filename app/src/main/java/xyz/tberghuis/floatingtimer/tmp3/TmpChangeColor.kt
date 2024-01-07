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
  Column {
    Text("tmp change color")
    Button(onClick = {
      vm.setResultAndPopHome(navController)
    }) {
      Text("button")
    }
  }
}

class TmpChangeColorVm(private val state: SavedStateHandle) : ViewModel() {
  val fdfsd = "fdsfs"


  fun setResultAndPopHome(navController: NavHostController) {
    logd("setResultAndPopHome")
    val bse = navController.previousBackStackEntry
    logd("setResultAndPopHome bse $bse")
    bse?.savedStateHandle?.set("custom_color", "willitblend")
    navController.popBackStack()
//    navController.popBackStack("home", false, true)
  }

}