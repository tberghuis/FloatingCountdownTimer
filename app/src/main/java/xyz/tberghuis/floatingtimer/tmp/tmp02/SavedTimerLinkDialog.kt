package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerLinkDialog(
  vmc: TmpSavedTimerDialogVmc
) {
  if (vmc.showLinkDialog == null) {
    return
  }

  val clipboardManager = LocalClipboardManager.current


  BasicAlertDialog(
    onDismissRequest = {
      vmc.showLinkDialog = null
    },
    modifier = Modifier,
  ) {
    Surface(
      modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight(),
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
      Column {
        Text("Timer Link")
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Checkbox(
            checked = vmc.start,
            onCheckedChange = { vmc.start = it }
          )
          Text("auto start")
        }

        Row {
          Button(onClick = { vmc.deepLinkToClipboard(clipboardManager) }) {
            Text("clipboard")
          }
          Button(onClick = {
            vmc.showLinkDialog = null
          }) {
            Text("cancel")
          }
        }
      }
    }
  }
}