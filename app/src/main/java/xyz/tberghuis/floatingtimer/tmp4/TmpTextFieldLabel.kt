package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.R


@Composable
fun TmpTextFieldLabel() {
  OutlinedTextField(
    value = "this is a long stringthis is a long stringthis is a long stringthis is a long string",
    onValueChange = {},
    modifier = Modifier,
    enabled = false,
    readOnly = true,
    label = {
      Text(stringResource(R.string.current))
    },
    colors = OutlinedTextFieldDefaults.colors(
      disabledTextColor = MaterialTheme.colorScheme.onSurface,
      disabledContainerColor = Color.Transparent,
      disabledBorderColor = MaterialTheme.colorScheme.outline,
      disabledLabelColor = MaterialTheme.colorScheme.primary,
    )
  )

  OutlinedTextField(
    value = "this is a long stringthis is a long stringthis is a long stringthis is a long string",
    onValueChange = {},
    modifier = Modifier,
    enabled = true,
    readOnly = true,
    label = {
      Text(stringResource(R.string.current))
    },
    colors = OutlinedTextFieldDefaults.colors(
      disabledTextColor = MaterialTheme.colorScheme.onSurface,
      disabledContainerColor = Color.Transparent,
      disabledBorderColor = MaterialTheme.colorScheme.outline,
      disabledLabelColor = MaterialTheme.colorScheme.primary,
    ),

    singleLine = true,


    )
}