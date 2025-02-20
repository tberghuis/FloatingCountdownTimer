package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.material3.BasicAlertDialog
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
      Text("hello options dialog")

    }

  }
}
