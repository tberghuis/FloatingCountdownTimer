package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpCountdownScreen(
  vm: TmpCountdownScreenVm = viewModel()
) {
  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Floating Timer") },
        modifier = Modifier,
      )
    },
    bottomBar = {
      TmpBottomBar(TmpScreenTypeCountdown)
    },
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) { padding ->
    TmpCountdownScreenContent(padding)
  }
  TmpGrantOverlayDialog(vm.grantOverlayVmc)
}

@Composable
fun TmpCountdownScreenContent(padding: PaddingValues) {
  Column(modifier = Modifier.padding(padding)) {
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
            vm.deleteSavedTimer(it)
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