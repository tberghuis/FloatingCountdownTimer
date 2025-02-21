package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.SavedTimerDialogVmc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTimerLinkDialog(
  vmc: SavedTimerDialogVmc
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
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(stringResource(R.string.timer_link))
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Checkbox(
            checked = vmc.start,
            onCheckedChange = { vmc.start = it }
          )
          Text(stringResource(R.string.auto_start))
        }
        Row(
          modifier = Modifier,
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          TextButton(onClick = { vmc.deepLinkToClipboard(clipboardManager) }) {
            Text(stringResource(R.string.clipboard))
          }
          TextButton(onClick = {
            vmc.showLinkDialog = null
          }) {
            Text(stringResource(R.string.cancel))
          }
        }
      }
    }
  }
}