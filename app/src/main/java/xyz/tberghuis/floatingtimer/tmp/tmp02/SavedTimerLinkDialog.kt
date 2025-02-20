package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerLinkDialog(
  vm: TmpCountdownScreenVm = viewModel()
) {
  if (vm.savedTimerDialogVmc.showLinkDialog == null) {
    return
  }

  BasicAlertDialog(
    onDismissRequest = {
      vm.savedTimerDialogVmc.showLinkDialog = null
    },
    modifier = Modifier,
  ) {
    Surface() {
      Column {
        Text("hello link dialog")
      }
    }
  }
}