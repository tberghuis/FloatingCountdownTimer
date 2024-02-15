package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.logd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpCountdownScreen(
  vm: TmpCountdownScreenVm = viewModel()
) {
  val focusManager = LocalFocusManager.current
  val navController = LocalNavController.current
  var showMenu by remember { mutableStateOf(false) }
  Scaffold(
    modifier = Modifier.pointerInput(Unit) {
      detectTapGestures(onTap = {
        focusManager.clearFocus()
        logd("on tap")
      })
    },
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = Modifier,
        actions = {
          IconButton(onClick = {
            showMenu = true
          }) {
            Icon(Icons.Filled.MoreVert, stringResource(R.string.settings))
          }
          DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
          ) {
            DropdownMenuItem(
              text = { Text(stringResource(R.string.default_color)) },
              onClick = {
                navController.navigate("change_color")
              },
            )
            DropdownMenuItem(
              text = { Text(stringResource(R.string.change_size)) },
              onClick = { navController.navigate("change_size") },
            )
          }
        },
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