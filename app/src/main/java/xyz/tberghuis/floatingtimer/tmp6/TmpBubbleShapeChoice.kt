package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimerShapeChoice(vm: TimerShapeChoiceVm) {
  Row {
    RadioButton(
      selected = vm.timerShape == "circle",
      onClick = { vm.timerShape = "circle" },
      modifier = Modifier,
    )
    Text("circle")

    RadioButton(
      selected = vm.timerShape == "rectangle",
      onClick = { vm.timerShape = "rectangle" },
      modifier = Modifier,
    )
    Text("rectangle")
  }
}