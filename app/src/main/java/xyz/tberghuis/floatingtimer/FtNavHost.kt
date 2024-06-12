package xyz.tberghuis.floatingtimer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.composables.GrantOverlayDialog
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen
import xyz.tberghuis.floatingtimer.screens.CountdownScreen
import xyz.tberghuis.floatingtimer.screens.SizeSettingScreen
import xyz.tberghuis.floatingtimer.screens.StopwatchScreen
import xyz.tberghuis.floatingtimer.viewmodels.CountdownScreenVm
import xyz.tberghuis.floatingtimer.viewmodels.StopwatchScreenVm
import android.Manifest
import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import xyz.tberghuis.floatingtimer.screens.PremiumScreen
import xyz.tberghuis.floatingtimer.tmp5.Tmp5RingtoneScreen

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
      composable("stopwatch") { entry ->
        val vm: StopwatchScreenVm = viewModel()
        entry.OnNavResult<Int>(savedStateHandleKey = "color_result") { result ->
          vm.haloColor = Color(result)
        }
        StopwatchScreen()
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
      composable("premium") {
        PremiumScreen()
      }

      composable("countdown_ringtone") {
        Tmp5RingtoneScreen()
      }
    }
  }

  GrantOverlayDialog()
  LaunchPostNotificationsPermissionRequest()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LaunchPostNotificationsPermissionRequest() {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
    return
  }
  val notificationsPermissionState =
    rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
  if (!notificationsPermissionState.status.isGranted && !notificationsPermissionState.status.shouldShowRationale) {
    LaunchedEffect(Unit) {
      notificationsPermissionState.launchPermissionRequest()
    }
  }
}