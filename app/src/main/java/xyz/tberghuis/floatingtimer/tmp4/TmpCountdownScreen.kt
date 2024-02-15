package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpCountdownScreen(
  vm: TmpCountdownScreenVm = viewModel()
) {
  val focusManager = LocalFocusManager.current

  Scaffold(
    modifier = Modifier.pointerInput(Unit) {
      detectTapGestures(onTap = {
        focusManager.clearFocus()
        logd("on tap")
      })
    },
    topBar = { TmpFtTopAppBar() },
    bottomBar = {
      TmpBottomBar(TmpScreenTypeCountdown)
    },
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) { padding ->
    TmpCountdownScreenContent(padding)
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_multiple_timers))
}

@Composable
fun TmpCountdownScreenContent(padding: PaddingValues) {
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(rememberScrollState())
  ) {
    TmpCreateCountdownCard()
    TmpSavedCountdownCard()
  }
  ConfirmDeleteSavedTimerDialog()
}

@Composable
fun ConfirmDeleteSavedTimerDialog(
  vm: TmpCountdownScreenVm = viewModel()
) {
  if (vm.showDeleteDialog == null) {
    return
  }
  AlertDialog(
    onDismissRequest = {
      vm.showDeleteDialog = null
    },
    confirmButton = {
      TextButton(
        onClick = {
          vm.showDeleteDialog?.let {
            vm.deleteSavedCountdown(it)
          }
          vm.showDeleteDialog = null
        }
      ) {
        Text("OK")
      }
    },
    modifier = Modifier,
    dismissButton = {
      TextButton(
        onClick = {
          vm.showDeleteDialog = null
        }
      ) {
        Text("Cancel")
      }
    },
    title = { Text("Delete Saved Timer?") },
  )
}