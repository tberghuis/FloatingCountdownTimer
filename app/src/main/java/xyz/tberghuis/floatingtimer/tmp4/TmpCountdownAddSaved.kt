package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun TmpAddSavedButton(
  onClick: () -> Unit
) {
  Button(onClick = onClick) {
    Icon(Icons.Default.Save, "add to saved")
  }
}