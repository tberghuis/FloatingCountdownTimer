package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R


@Composable
fun TmpTextFieldLabel() {

  val labelTfvState = remember {
    mutableStateOf(TextFieldValue(""))
  }

  TextField(
    value = labelTfvState.value,
    onValueChange = {
      labelTfvState.value = it
    },
    modifier = Modifier
      .widthIn(max = 150.dp),
    label = { Text(stringResource(R.string.label)) }
  )




  TextField(
    value = labelTfvState.value,
    onValueChange = {
      labelTfvState.value = it
    },
    modifier = Modifier
      .widthIn(max = 150.dp),
    label = { Text(stringResource(R.string.label)) },


    singleLine = true,

    )


}