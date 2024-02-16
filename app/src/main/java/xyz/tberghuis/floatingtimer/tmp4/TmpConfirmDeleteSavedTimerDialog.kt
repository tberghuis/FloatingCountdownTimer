package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmDeleteSavedTimerDialog(
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
        Text("OK")
      }
    },
    modifier = Modifier,
    dismissButton = {
      TextButton(
        onClick = onDismiss
      ) {
        Text("Cancel")
      }
    },
    title = { Text("Delete Saved Timer?") },
  )
}