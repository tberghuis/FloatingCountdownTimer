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
import xyz.tberghuis.floatingtimer.composables.CreateStopwatchCard
import xyz.tberghuis.floatingtimer.composables.FtBottomBar
import xyz.tberghuis.floatingtimer.composables.FtTopAppBar
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.composables.SavedTimersCard
import xyz.tberghuis.floatingtimer.composables.SavedTimerLinkDialog
import xyz.tberghuis.floatingtimer.tmp.tmp02.SavedTimerOptionsDialog
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm
import xyz.tberghuis.floatingtimer.viewmodels.StopwatchScreenVm

@Composable
fun StopwatchScreen(
  vm: StopwatchScreenVm = viewModel()
) {
  val localFocusManager = LocalFocusManager.current
  Scaffold(
    modifier = Modifier.pointerInput(Unit) {
      detectTapGestures(onTap = {
        localFocusManager.clearFocus()
      })
    },
    topBar = { FtTopAppBar() },
    bottomBar = {
      FtBottomBar(ScreenTypeStopwatch)
    },
  ) { padding ->
    StopwatchScreenContent(padding)
  }
  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_multiple_timers))
}

@Composable
fun StopwatchScreenContent(
  padding: PaddingValues,
  vm: StopwatchScreenVm = viewModel()
) {
  val context = LocalContext.current
  val sharedVm: SharedVm = viewModel(context as ComponentActivity)
  val savedTimers by vm.savedStopwatchFlow().collectAsState(
    initial = listOf()
  )
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(rememberScrollState())
  ) {
    CreateStopwatchCard()
    SavedTimersCard(
      savedTimers = savedTimers,
      timerOnClick = { savedTimer ->
        // how to DRY??? that is the question... meh
        // write a plain function that take sharedVm, context ....
        if (!Settings.canDrawOverlays(context)) {
          sharedVm.showGrantOverlayDialog = true
          return@SavedTimersCard
        }
        vm.savedStopwatchClick(savedTimer)
      },
      timerOnLongClick = { savedTimer ->
        vm.savedTimerDialogVmc.showOptionsDialog = savedTimer
      },
    )
  }
  SavedTimerOptionsDialog(vm.savedTimerDialogVmc)
  SavedTimerLinkDialog(vm.savedTimerDialogVmc)
}