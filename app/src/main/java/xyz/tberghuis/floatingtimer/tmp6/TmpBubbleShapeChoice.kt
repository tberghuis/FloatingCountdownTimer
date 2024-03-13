package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BubbleShapeChoice(vm: BubbleShapeChoiceVm) {
  Row {
    RadioButton(
      selected = vm.bubbleShapeChoice == "circle",
      onClick = { vm.bubbleShapeChoice = "circle" },
      modifier = Modifier,
    )
    Text("circle")

    RadioButton(
      selected = vm.bubbleShapeChoice == "rectangle",
      onClick = { vm.bubbleShapeChoice = "rectangle" },
      modifier = Modifier,
    )
    Text("rectangle")
  }
}