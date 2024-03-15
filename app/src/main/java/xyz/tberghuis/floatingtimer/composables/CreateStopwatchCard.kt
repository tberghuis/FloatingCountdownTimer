package xyz.tberghuis.floatingtimer.composables

import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm
import xyz.tberghuis.floatingtimer.viewmodels.StopwatchScreenVm

@Composable
fun CreateStopwatchCard() {
  val context = LocalContext.current
  val sharedVm: SharedVm = viewModel(context as ComponentActivity)
  val vm: StopwatchScreenVm = viewModel()

  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
  ) {
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
      Text(stringResource(id = R.string.stopwatch), fontSize = 20.sp)
    }

    TimerShapeChoice(vm)

    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ChangeTimerColorButton("change_color/stopwatch", vm.haloColor)
      AddSavedButton {
        vm.addToSaved()
      }
      Button(modifier = Modifier.testTag("stopwatch_create"), onClick = {
        logd("start stopwatch")
        if (!Settings.canDrawOverlays(context)) {
          sharedVm.showGrantOverlayDialog = true
          return@Button
        }
        vm.stopwatchButtonClick()
      }) {
        Text(stringResource(id = R.string.create))
      }
    }
  }
}