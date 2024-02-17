package xyz.tberghuis.floatingtimer.tmp4

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.ConfirmDeleteSavedTimerDialog
import xyz.tberghuis.floatingtimer.composables.CreateStopwatchCard
import xyz.tberghuis.floatingtimer.composables.FtBottomBar
import xyz.tberghuis.floatingtimer.composables.FtTopAppBar
import xyz.tberghuis.floatingtimer.composables.PremiumDialog

@Composable
fun TmpStopwatchScreen(
  vm: TmpStopwatchScreenVm = viewModel()
) {
  Scaffold(
    modifier = Modifier,
    topBar = { FtTopAppBar() },
    bottomBar = {
      FtBottomBar(TmpScreenTypeStopwatch)
    },
  ) { padding ->
    TmpStopwatchScreenContent(padding)
  }
  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_multiple_timers))
}

@Composable
fun TmpStopwatchScreenContent(
  padding: PaddingValues,
  vm: TmpStopwatchScreenVm = viewModel()
) {
  val savedTimers by vm.savedStopwatchFlow().collectAsState(
    initial = listOf()
  )
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(rememberScrollState())
  ) {
    CreateStopwatchCard()
    TmpSavedTimersCard(
      savedTimers = savedTimers,
      timerOnClick = { savedTimer ->
        vm.savedStopwatchClick(savedTimer)
      },
      timerOnLongClick = { savedTimer ->
        vm.showDeleteDialog = savedTimer
      },
    )
  }
  ConfirmDeleteSavedTimerDialog(
    showDialog = vm.showDeleteDialog != null,
    onDismiss = { vm.showDeleteDialog = null },
    onConfirm = {
      vm.showDeleteDialog?.let {
        vm.deleteSavedStopwatch(it)
      }
      vm.showDeleteDialog = null
    }
  )
}