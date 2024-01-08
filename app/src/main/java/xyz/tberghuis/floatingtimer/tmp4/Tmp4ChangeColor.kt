package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun Tmp4ChangeColor(
  vm: Tmp4ChangeColorVm = viewModel(),
  ) {
  val navController = LocalNavController.current
  val homeBackStackEntry = navController.previousBackStackEntry!!

  Column {
    Text("tmp change color")
    Button(onClick = {
      homeBackStackEntry.savedStateHandle["custom_color"] = "will it blend"
      navController.popBackStack()
    }) {
      Text("button")
    }
  }
}

class Tmp4ChangeColorVm(private val state: SavedStateHandle) : ViewModel() {

}