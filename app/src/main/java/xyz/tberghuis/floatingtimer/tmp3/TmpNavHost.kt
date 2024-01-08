package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun MyNavHost(
) {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") {
        Home()
      }
      composable("login") {
        Login()
      }
      composable("tmp_change_color") {
        TmpChangeColor()
      }
    }
  }
}