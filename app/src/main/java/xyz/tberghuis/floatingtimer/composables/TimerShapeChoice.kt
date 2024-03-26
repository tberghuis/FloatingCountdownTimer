package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Rectangle
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.TimerShapeChoiceVm

@Composable
fun ColumnScope.TimerShapeChoice(vm: TimerShapeChoiceVm) {
  val focusRequester = remember { FocusRequester() }
  if (vm.timerShape == "label") {
    LaunchedEffect(Unit) {
      focusRequester.requestFocus()
    }
  }

  Row(
    modifier = Modifier
      .align(Alignment.CenterHorizontally)
      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(stringResource(R.string.shape_label))
    Column {
      Row {
        RadioButton(
          selected = vm.timerShape == "circle",
          onClick = { vm.timerShape = "circle" },
          modifier = Modifier,
        )
        Icon(
          Icons.Outlined.Circle,
          contentDescription = stringResource(R.string.circle),
          modifier = Modifier.size(40.dp)
        )

        RadioButton(
          selected = vm.timerShape == "rectangle",
          onClick = { vm.timerShape = "rectangle" },
          modifier = Modifier,
        )
        Icon(
          Icons.Outlined.Rectangle,
          contentDescription = stringResource(R.string.rectangle),
          modifier = Modifier.size(40.dp)
        )
      }
      Row {
        RadioButton(
          selected = vm.timerShape == "label",
          onClick = {
            logd("radiobutton clickable")
            vm.timerShape = "label"
          },
          modifier = Modifier,
        )
        TextField(
          value = vm.label,
          onValueChange = { vm.label = it },
          modifier = Modifier
            .widthIn(max = 150.dp)
            .focusRequester(focusRequester)
            .clickable {
              vm.timerShape = "label"
            },
          enabled = vm.timerShape == "label",
          label = { Text(stringResource(R.string.label)) }
        )
      }
    }
  }
}

@Preview
@Composable
fun PreviewTmpTimerShapeChoice() {
  val vm = remember {
    object : TimerShapeChoiceVm {
      override var timerShape by mutableStateOf("circle")
      override var label by mutableStateOf("")
    }
  }
  val localFocusManager = LocalFocusManager.current
  Column(
    Modifier
      .fillMaxSize()
      .pointerInput(Unit) {
        logd("pointerInput")
        detectTapGestures(onTap = {
          logd("detectTapGestures")
          localFocusManager.clearFocus()
        })
      }
  ) {
    TimerShapeChoice(vm)
  }
}