package xyz.tberghuis.floatingtimer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  vm: SettingsViewModel = viewModel()
) {
  val navController = LocalNavController.current

  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Settings") },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding),
    ) {
      Text("Change Timer color")
      Button(onClick = {
        logd("change timer color")
        if (vm.haloColourPurchased) {
          // todo
//          vm.save()
        } else {
          vm.showPurchaseDialog = true
        }
      }) {
        Text("SAVE")
      }
    }
  }

  if (vm.showPurchaseDialog) {
    AlertDialog(
      onDismissRequest = {
        vm.showPurchaseDialog = false
      },
      confirmButton = {
        TextButton(onClick = { /* TODO */ }) {
          Text("Buy".uppercase())
        }
      },
      modifier = Modifier,
      dismissButton = {
        TextButton(onClick = { vm.showPurchaseDialog = false }) {
          Text("Cancel".uppercase())
        }
      },
      title = { Text("Premium Feature") },
      text = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text("Change Timer halo color")
          Text(vm.haloColorChangePriceText)
        }
      },
    )
  }
}