package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen
import xyz.tberghuis.floatingtimer.screens.LaunchPostNotificationsPermissionRequest
import xyz.tberghuis.floatingtimer.screens.SizeSettingScreen
import xyz.tberghuis.floatingtimer.tmp5.OnNavResult

@Composable
fun TmpNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "countdown"
    ) {
      composable("countdown") { entry ->
        val vm: TmpCountdownScreenVm = viewModel()
        entry.OnNavResult<Int>(savedStateHandleKey = "color_result") { result ->
          vm.haloColor = Color(result)
        }
        TmpCountdownScreen()
      }
      composable("stopwatch") {entry ->
        val vm: TmpStopwatchScreenVm = viewModel()
        entry.OnNavResult<Int>(savedStateHandleKey = "color_result") { result ->
          vm.haloColor = Color(result)
        }
        TmpStopwatchScreen()
      }
      composable("change_color/{timerType}") {
        ColorSettingScreen()
      }
      composable("change_color") {
        ColorSettingScreen()
      }
      composable("change_size") {
        SizeSettingScreen()
      }
    }
  }

  TmpGrantOverlayDialog()
  LaunchPostNotificationsPermissionRequest()
}