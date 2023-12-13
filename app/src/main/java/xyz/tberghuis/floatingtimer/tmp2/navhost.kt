package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen

@Composable
fun TmpNavHost(
) {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") {
        TmpHome()
      }
      composable("change_size") {
        ChangeSizeScreen()
      }
      composable("change_color") {
        ColorSettingScreen()
      }
    }
  }
}