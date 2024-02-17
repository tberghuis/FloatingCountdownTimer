package xyz.tberghuis.floatingtimer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.OnNavResult
import xyz.tberghuis.floatingtimer.composables.GrantOverlayDialog
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen
import xyz.tberghuis.floatingtimer.screens.CountdownScreen
import xyz.tberghuis.floatingtimer.screens.LaunchPostNotificationsPermissionRequest
import xyz.tberghuis.floatingtimer.screens.SizeSettingScreen
import xyz.tberghuis.floatingtimer.tmp4.TmpStopwatchScreen
import xyz.tberghuis.floatingtimer.tmp4.TmpStopwatchScreenVm
import xyz.tberghuis.floatingtimer.viewmodels.CountdownScreenVm

@Composable
fun FtNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "countdown"
    ) {
      composable("countdown") { entry ->
        val vm: CountdownScreenVm = viewModel()
        entry.OnNavResult<Int>(savedStateHandleKey = "color_result") { result ->
          vm.haloColor = Color(result)
        }
        CountdownScreen()
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

  GrantOverlayDialog()
  LaunchPostNotificationsPermissionRequest()
}