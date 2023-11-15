package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column {
    Text("tmp screen")

    Button(onClick = {
      vm.addStopwatch()
    }) {
      Text("addStopwatch")
    }

    Button(onClick = {
      vm.addCountdown()
    }) {
      Text("addCountdown")
    }

    Button(onClick = {
      vm.exitAll()
    }) {
      Text("exit all timers")
    }

    Button(onClick = {
      vm.showDialogFn()
    }) {
      Text("show dialog")
    }
  }

  if (vm.showDialog) {
    AlertDialog(
      onDismissRequest = {
        vm.showDialog = false
      },
      confirmButton = {
        TextButton(onClick = {
        }) {
          Text(stringResource(R.string.buy).uppercase())
        }
      },
      modifier = Modifier,
      dismissButton = {
        TextButton(onClick = { vm.showDialog = false }) {
          Text(stringResource(R.string.cancel).uppercase())
        }
      },
      title = { Text(stringResource(R.string.premium_feature)) },
      text = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text(stringResource(R.string.change_timer_halo_color))
          Text("\$X.XX")
        }
      },
    )
  }
}