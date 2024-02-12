package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TmpCountdownAddSaved(
  vm: TmpCountdownScreenVm = viewModel()
) {
  Button(onClick = {
    vm.addToSaved()
  }) {
    Icon(Icons.Default.Save, "add to saved")
  }
}