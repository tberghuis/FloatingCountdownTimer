package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController


@Composable
fun TmpNavHost(
) {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") {
        TmpNavigateResult()
      }

      composable("change_color") {
        TmpChangeColor()
      }
    }
  }
}


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