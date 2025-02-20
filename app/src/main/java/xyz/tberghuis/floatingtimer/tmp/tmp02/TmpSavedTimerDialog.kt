package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerOptionsDialog(
  vm: TmpCountdownScreenVm = viewModel()
) {
  if (vm.savedTimerDialogVmc.showOptionsDialog == null) {
    return
  }

  BasicAlertDialog(
    onDismissRequest = {
      vm.savedTimerDialogVmc.showOptionsDialog = null
    },
    modifier = Modifier,
  ) {
    Surface() {
      Column {
        Text("Saved Timer Options")
        Row {
          Button(onClick = {
            vm.savedTimerDialogVmc.showLinkDialog = vm.savedTimerDialogVmc.showOptionsDialog
            vm.savedTimerDialogVmc.showOptionsDialog = null
          }) {
            Text("link")
          }
          Button(onClick = {
            //todo
          }) {
            Text("delete")
          }
          Button(onClick = {
            vm.savedTimerDialogVmc.showOptionsDialog = null
          }) {
            Text("cancel")
          }
        }
      }
    }
  }
}