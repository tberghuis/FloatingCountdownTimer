package xyz.tberghuis.floatingtimer.screens

import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.CreateCountdownCard
import xyz.tberghuis.floatingtimer.composables.FtBottomBar
import xyz.tberghuis.floatingtimer.composables.FtTopAppBar
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.composables.SavedTimersCard
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.tmp02.SavedTimerLinkDialog
import xyz.tberghuis.floatingtimer.tmp.tmp02.SavedTimerOptionsDialog
import xyz.tberghuis.floatingtimer.viewmodels.CountdownScreenVm
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm

@Composable
fun CountdownScreen(
  vm: CountdownScreenVm = viewModel()
) {
  val focusManager = LocalFocusManager.current

  Scaffold(
    modifier = Modifier.pointerInput(Unit) {
      detectTapGestures(onTap = {
        focusManager.clearFocus()
        logd("on tap")
      })
    },
    topBar = { FtTopAppBar() },
    bottomBar = {
      FtBottomBar(ScreenTypeCountdown)
    },
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) { padding ->
    CountdownScreenContent(padding)
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_multiple_timers))
}

@Composable
fun CountdownScreenContent(
  padding: PaddingValues,
  vm: CountdownScreenVm = viewModel()
) {
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current
  val sharedVm: SharedVm = viewModel(context as ComponentActivity)
  val savedTimers by vm.savedCountdownFlow().collectAsState(
    initial = listOf()
  )
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(rememberScrollState())
  ) {
    CreateCountdownCard()
    SavedTimersCard(
      savedTimers = savedTimers,
      timerOnClick = { savedTimer ->
        // remove focus from TextField
        focusManager.clearFocus()
        if (!Settings.canDrawOverlays(context)) {
          sharedVm.showGrantOverlayDialog = true
          return@SavedTimersCard
        }
        vm.savedCountdownClick(savedTimer)
      },
      timerOnLongClick = { savedTimer ->
        // remove focus from TextField
        focusManager.clearFocus()
        vm.savedTimerDialogVmc.showOptionsDialog = savedTimer
      },
    )
  }
  SavedTimerOptionsDialog(vm.savedTimerDialogVmc)
  SavedTimerLinkDialog(vm.savedTimerDialogVmc)
}