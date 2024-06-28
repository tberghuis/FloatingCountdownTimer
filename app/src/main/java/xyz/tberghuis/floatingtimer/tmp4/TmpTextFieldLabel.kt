package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R


@Composable
fun TmpTextFieldLabel() {

  TextField(
    modifier = Modifier
      .width(100.dp),
    label = { Text("secondssadasd") },
    value = "0",
    onValueChange = { },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    singleLine = true
  )


  TextField(
    modifier = Modifier
      .width(100.dp),
    label = {
      Text(
        "secondssadasd",

        maxLines = 1,
      )
    },
    value = "0",
    onValueChange = { },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    singleLine = true
  )

}