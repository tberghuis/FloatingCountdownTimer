package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun Tmp4NavigateResult(
  navController: NavHostController = LocalNavController.current,
  vm: Tmp4NavigateResultVm = viewModel()
) {
  val customColor by navController.currentBackStackEntry!!.savedStateHandle.getStateFlow<String?>(
    "custom_color",
    null
  ).collectAsState()

  Column {
    Text("custom color $customColor")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

class Tmp4NavigateResultVm() : ViewModel() {

}