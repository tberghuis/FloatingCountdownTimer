package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerOptionsDialog(
  vmc: TmpSavedTimerDialogVmc
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
    Surface(
      modifier = Modifier,
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
      ) {
        Text("Saved Timer Options")
        Row {
          Button(onClick = {
            vmc.showLinkDialog = vmc.showOptionsDialog
            vmc.showOptionsDialog = null
          }) {
            Text("link")
          }
          Button(onClick = { vmc.deleteSavedTimer() }) {
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