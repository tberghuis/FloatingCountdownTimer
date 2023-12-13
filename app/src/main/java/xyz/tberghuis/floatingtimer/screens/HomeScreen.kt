package xyz.tberghuis.floatingtimer.screens

import android.Manifest
import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION
import xyz.tberghuis.floatingtimer.composables.CancelAllTimersCard
import xyz.tberghuis.floatingtimer.composables.CreateCountdownCard
import xyz.tberghuis.floatingtimer.composables.CreateStopwatchCard
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XxxHomeScreen() {
  LaunchPostNotificationsPermissionRequest()

  val vm: HomeViewModel = viewModel()
  val context = LocalContext.current
  val navController = LocalNavController.current

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = Modifier,
        actions = {
          IconButton(onClick = {
            navController.navigate("settings")
          }) {
            Icon(Icons.Filled.Settings, stringResource(R.string.settings))
          }
        },
      )
    },
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) {
    HomeScreenContent(it)
  }

  if (vm.showGrantOverlayDialog) {
    AlertDialog(onDismissRequest = {
      vm.showGrantOverlayDialog = false
    }, confirmButton = {
      Button(onClick = {
        logd("go to settings")
        val intent = Intent(
          Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName)
        )

        startActivityForResult(
          context as Activity, intent, REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION, null
        )
        vm.showGrantOverlayDialog = false

      }) {
        Text(stringResource(R.string.go_to_settings))
      }
    }, title = {
      Text(stringResource(R.string.enable_overlay_permission))
    }, text = {
      Text(buildAnnotatedString {
        append(stringResource(R.string.dialog_enable_overlay_permission))
        append(" ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
          append(stringResource(R.string.app_name))
        }
      })
    })
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_multiple_timers))
}

@Composable
fun HomeScreenContent(paddingValues: PaddingValues) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier
      .padding(paddingValues)
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .pointerInput(Unit) {
        detectTapGestures(onTap = {
          focusManager.clearFocus()
          logd("on tap")
        })
      },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CreateCountdownCard()
    CreateStopwatchCard()
    CancelAllTimersCard()
  }
}

fun Modifier.onFocusSelectAll(textFieldValueState: MutableState<TextFieldValue>): Modifier =
  composed(inspectorInfo = debugInspectorInfo {
    name = "textFieldValueState"
    properties["textFieldValueState"] = textFieldValueState
  }) {
    var triggerEffect by remember {
      mutableStateOf<Boolean?>(null)
    }
    if (triggerEffect != null) {
      LaunchedEffect(triggerEffect) {
        val tfv = textFieldValueState.value
        textFieldValueState.value = tfv.copy(selection = TextRange(0, tfv.text.length))
      }
    }
    Modifier.onFocusChanged { focusState ->
      if (focusState.isFocused) {
        triggerEffect = triggerEffect?.let { bool ->
          !bool
        } ?: true
      }
    }
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