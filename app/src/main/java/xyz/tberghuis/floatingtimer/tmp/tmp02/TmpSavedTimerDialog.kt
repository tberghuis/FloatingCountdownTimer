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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerOptionsDialog(
  vmc: TmpSavedTimerDialogVmc,
  onDelete: () -> Unit
) {
  if (vmc.showOptionsDialog == null) {
    return
  }

  BasicAlertDialog(
    onDismissRequest = {
      vmc.showOptionsDialog = null
    },
    modifier = Modifier,
  ) {
    Surface() {
      Column {
        Text("Saved Timer Options")
        Row {
          Button(onClick = {
            vmc.showLinkDialog = vmc.showOptionsDialog
            vmc.showOptionsDialog = null
          }) {
            Text("link")
          }
          Button(onClick = onDelete) {
            Text("delete")
          }
          Button(onClick = {
            vmc.showOptionsDialog = null
          }) {
            Text("cancel")
          }
        }
      }
    }
  }
}