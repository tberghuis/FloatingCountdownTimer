package xyz.tberghuis.floatingtimer.tmp5

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun Tmp5NavHost(
) {

  val colorSharedVm: ColorSharedVm = viewModel(LocalContext.current as ComponentActivity)

  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") { entry ->
        // this is wack
        val color = entry.savedStateHandle.get<String>("custom_color")
        val vm: Tmp5NavigateResultVm = viewModel()
        if (color != null) {
          LaunchedEffect(Unit) {
            // or i could run vm.onResult(color)
            vm.color = color
            entry.savedStateHandle["custom_color"] = null
          }
        }

        Tmp5NavigateResult()
      }

      composable("change_color") { entry ->
        val homeVm: Tmp5NavigateResultVm = viewModel(navController.previousBackStackEntry!!)

        Tmp5ChangeColor()
      }
    }
  }


}