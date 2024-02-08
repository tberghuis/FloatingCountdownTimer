package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun TmpNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "countdown"
    ) {
      composable("countdown") {
        Text("countdown screen")
      }
      composable("stopwatch") {
        Text("stopwatch screen")
      }
    }
  }
}