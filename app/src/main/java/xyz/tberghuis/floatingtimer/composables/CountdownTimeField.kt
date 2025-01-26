package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.CountdownTimeField(
  tfvState: MutableState<TextFieldValue>,
  label: String,
  testTag: String? = null,
  imeAction: ImeAction = ImeAction.Unspecified
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
      imeAction = imeAction
    ),
    singleLine = true
  )
}