package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen

@Composable
fun TmpNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "countdown"
    ) {
      composable("countdown") {
        // future.txt LaunchedEffect to collect nav_result ...
        TmpCountdownScreen()
      }
      composable("stopwatch") {
        Text("stopwatch screen")
      }
      composable("change_color/{timerType}") {
        ColorSettingScreen()
      }
    }
  }
}