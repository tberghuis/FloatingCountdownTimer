package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Rectangle
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.TimerShapeChoiceVm

@Composable
fun ColumnScope.TmpTimerShapeChoice(vm: TimerShapeChoiceVm) {
  Row(
    modifier = Modifier.align(Alignment.CenterHorizontally),
//    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(stringResource(R.string.shape_label))
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



    RadioButton(
      selected = vm.timerShape == "label",
      onClick = { vm.timerShape = "label" },
      modifier = Modifier,
    )
    Text(stringResource(R.string.label))


  }
}