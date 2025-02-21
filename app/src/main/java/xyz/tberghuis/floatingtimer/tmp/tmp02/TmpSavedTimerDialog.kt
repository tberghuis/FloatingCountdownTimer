package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
         verticalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        Text("Saved Timer Options")
        Row(
          modifier = Modifier,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {

          IconButton(
            onClick = {
              vmc.showLinkDialog = vmc.showOptionsDialog
              vmc.showOptionsDialog = null
            },
          ) {
            Icon(Icons.Default.Link, "link")
          }



          IconButton(
            onClick = {
              vmc.deleteSavedTimer()
            },
          ) {
            Icon(Icons.Default.Delete, "delete")
          }

          TextButton(onClick = {
            vmc.showOptionsDialog = null
          }) {
            Text("cancel")
          }
        }
      }
    }
  }
}