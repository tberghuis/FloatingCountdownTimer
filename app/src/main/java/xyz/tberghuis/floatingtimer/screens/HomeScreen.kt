package xyz.tberghuis.floatingtimer.screens

import android.Manifest
import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import androidx.compose.runtime.Composable

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