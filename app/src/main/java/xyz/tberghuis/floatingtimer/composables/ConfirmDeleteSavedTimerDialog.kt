package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.R

@Composable
fun XxxConfirmDeleteSavedTimerDialog(
  showDialog: Boolean,
  onDismiss: () -> Unit,
  onConfirm: () -> Unit
) {
  if (!showDialog) {
    return
  }
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(
        onClick = onConfirm
      ) {
        Text(stringResource(R.string.delete))
      }
    },
    modifier = Modifier,
    dismissButton = {
      TextButton(
        onClick = onDismiss
      ) {
        Text(stringResource(R.string.cancel))
      }
    },
    title = { Text(stringResource(R.string.delete_saved_timer)) },
  )
}