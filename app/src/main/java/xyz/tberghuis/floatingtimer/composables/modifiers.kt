package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

// todo rewrite in CMF style
// https://engineering.teknasyon.com/composable-modifier-vs-composed-factory-in-jetpack-compose-6cbb675b0e7b
// update SO answer
fun Modifier.onFocusSelectAll(textFieldValueState: MutableState<TextFieldValue>): Modifier =
  composed(inspectorInfo = debugInspectorInfo {
    name = "textFieldValueState"
    properties["textFieldValueState"] = textFieldValueState
  }) {
    var triggerEffect by remember {
      mutableStateOf<Boolean?>(null)
    }
    if (triggerEffect != null) {
      LaunchedEffect(triggerEffect) {
        val tfv = textFieldValueState.value
        textFieldValueState.value = tfv.copy(selection = TextRange(0, tfv.text.length))
      }
    }
    onFocusChanged { focusState ->
      if (focusState.isFocused) {
        triggerEffect = triggerEffect?.let { bool ->
          !bool
        } ?: true
      }
    }
  }

@Composable
fun Modifier.runOnceOnGloballyPositioned(runOnce: (LayoutCoordinates) -> Unit): Modifier {
  var hasRun by remember { mutableStateOf(false) }
  return this then Modifier.onGloballyPositioned {
    if (!hasRun) {
      hasRun = true
      runOnce(it)
    }
  }
}