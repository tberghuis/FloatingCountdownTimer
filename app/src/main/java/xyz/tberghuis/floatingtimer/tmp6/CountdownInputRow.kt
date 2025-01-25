package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.TextFieldValue
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.onFocusSelectAll

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CountdownInputRow(
  hours: MutableState<TextFieldValue>,
  minutes: MutableState<TextFieldValue>,
  seconds: MutableState<TextFieldValue>
) {
  Row(
    modifier = Modifier
      .padding(10.dp)
      .fillMaxWidth(), horizontalArrangement = Arrangement.Center
  ) {
    TextField(
      modifier = Modifier
        .width(100.dp)
        .onFocusSelectAll(minutes)
        .semantics { testTagsAsResourceId = true }
        .testTag("CountdownMinutes"),
      label = { Text(stringResource(R.string.minutes), maxLines = 1) },
      value = minutes.value,
      onValueChange = { minutes.value = it },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
      ),
      singleLine = true
    )
    Spacer(Modifier.width(20.dp))
    TextField(
      modifier = Modifier
        .width(100.dp)
        .onFocusSelectAll(seconds),
      label = { Text(stringResource(R.string.seconds), maxLines = 1) },
      value = seconds.value,
      onValueChange = { seconds.value = it },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
      ),
      singleLine = true
    )
  }
}
