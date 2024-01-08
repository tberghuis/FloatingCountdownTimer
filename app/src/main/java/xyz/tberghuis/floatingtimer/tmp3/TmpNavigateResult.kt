package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpNavigateResult(
  navController: NavHostController = LocalNavController.current,
  vm: TmpNavigateResultVm = viewModel(navController.currentBackStackEntry!!)
) {


  val customColor by vm.customColor.collectAsState()

  Column {
    Text("custom color: ${customColor}")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

class TmpNavigateResultVm(val state: SavedStateHandle) : ViewModel() {
  val customColor = state.getStateFlow("custom_color", "defaultValue")

  init {
    logd("TmpNavigateResultVm init")
  }
}