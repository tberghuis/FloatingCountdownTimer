package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.CountdownScreenVm

@Composable
fun ColumnScope.TmpCountdownOptions() {
  val vm: CountdownScreenVm = viewModel()

  // doitwrong
  val vibration = vm.vibrationFlow.collectAsState(false)
  val sound = vm.soundFlow.collectAsState(false)

  Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
    BackgroundTransCheckbox(vm)
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = vibration.value,
        onCheckedChange = {
          vm.updateVibration(it)
        }
      )
      Text(stringResource(R.string.vibration))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = sound.value,
        onCheckedChange = {
          vm.updateSound(it)
        }
      )
      Text(stringResource(R.string.sound))
    }
  }
}