package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun Tmp5NavigateResult(
  navController: NavHostController = LocalNavController.current,
  vm: Tmp5NavigateResultVm = viewModel()
) {
  Column {
    Text("custom color ${vm.color}")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

class Tmp5NavigateResultVm() : ViewModel() {
  var color by mutableStateOf<String?>(null)
}