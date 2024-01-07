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
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpNavigateResult(
  vm: TmpNavigateResultVm = viewModel()
) {
  val navController = LocalNavController.current

  val customColor by vm.customColor.collectAsState()


  var willitblend by remember { mutableStateOf("initial") }

  LaunchedEffect(vm) {
    vm.customColor.collect {
      willitblend = it
    }
  }

  val backStackEntryCustomColor by
  navController.currentBackStackEntry!!.savedStateHandle.getStateFlow(
    "change_color",
    "bse initial"
  ).collectAsState("bse initial 2")


  LaunchedEffect(Unit) {
    logd(
      "navController.currentBackStackEntry!!.savedStateHandle ${
        navController.currentBackStackEntry!!.savedStateHandle.get<String>(
          "change_color",
        )
      }"
    )
  }


  Column {
    Text("tmp nav result ${vm.fdfsd}")
    Text("custom color: ${customColor}")
    Text("willitblend: ${willitblend}")

    Text("backStackEntryCustomColor $backStackEntryCustomColor")

    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }


    Button(onClick = {
      val _customColor =
        navController.currentBackStackEntry?.savedStateHandle?.get<String>(
          "custom_color"
        )
      logd("_customColor $_customColor")

      logd("backStackEntryCustomColor ${backStackEntryCustomColor.isNullOrEmpty()}")

      logd("vm savedStateHandle ${vm.state.get<String>("custom_color")}")
    }) {
      Text("check change color")
    }

  }
}

class TmpNavigateResultVm(val state: SavedStateHandle) : ViewModel() {
  val fdfsd = "fdsfs"

  val customColor = state.getStateFlow("custom_color", "defaultValue")

  val colorMutableStateFlow = MutableStateFlow("initial vm")


  init {
    logd("TmpNavigateResultVm init")


  }


}