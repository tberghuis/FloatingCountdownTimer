package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumDialog

@Composable
fun TmpStopwatchScreen(
  vm: TmpStopwatchScreenVm = viewModel()
) {
  Scaffold(
    modifier = Modifier,
    topBar = { TmpFtTopAppBar() },
    bottomBar = {
      TmpBottomBar(TmpScreenTypeStopwatch)
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
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(rememberScrollState())
  ) {
    TmpCreateStopwatchCard()
//    TmpSavedCountdownCard()
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