package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

@Composable
fun Tmp5NavHost(
) {


  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") {

        Tmp5NavigateResult()
      }

      composable("change_color") {
        Tmp5ChangeColor()
      }
    }
  }


}