package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.onFocusSelectAll

@Composable
fun CountdownInputRow(
  hours: MutableState<TextFieldValue>,
  minutes: MutableState<TextFieldValue>,
  seconds: MutableState<TextFieldValue>
) {
  Row(
    modifier = Modifier
      .padding(10.dp)
      .widthIn(max = 350.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
  ) {
    CountdownTimeField(hours, stringResource(R.string.hours))
    CountdownTimeField(minutes, stringResource(R.string.minutes), "CountdownMinutes")
    CountdownTimeField(seconds, stringResource(R.string.seconds))
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.CountdownTimeField(
  tfvState: MutableState<TextFieldValue>,
  label: String,
  testTag: String? = null
) {
  val testTagModifier = testTag?.let {
    Modifier
      .semantics { testTagsAsResourceId = true }
      .testTag(testTag)
  } ?: Modifier

  TextField(
    modifier = Modifier
      .weight(1f)
      .onFocusSelectAll(tfvState)
      .onFocusChanged {
        if (!it.isFocused && tfvState.value.text.isBlank()) {
          tfvState.value = TextFieldValue("0")
        }
      }
      .then(testTagModifier),
    label = { Text(label, maxLines = 1) },
    value = tfvState.value,
    onValueChange = { tfvState.value = it },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number,
      imeAction = ImeAction.Next
    ),
    singleLine = true
  )
}

@Preview
@Composable
fun CountdownInputRowPreview() {

  val hours = remember {
    mutableStateOf(TextFieldValue("0"))
  }
  val minutes = remember {
    mutableStateOf(TextFieldValue("0"))
  }
  val seconds = remember {
    mutableStateOf(TextFieldValue("0"))
  }


  Box(
    modifier = Modifier
      .background(Color.Blue.copy(alpha = .2f))
      .fillMaxWidth(.8f),
    contentAlignment = Alignment.Center,
  ) {
    CountdownInputRow(hours, minutes, seconds)
  }
}