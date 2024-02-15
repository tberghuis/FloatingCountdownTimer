package xyz.tberghuis.floatingtimer.tmp4

import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import xyz.tberghuis.floatingtimer.composables.ChangeTimerColorButton
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpCreateStopwatchCard() {
  val context = LocalContext.current
  val sharedVm: TmpSharedVm = viewModel(context as ComponentActivity)
  val vm: TmpStopwatchScreenVm = viewModel()

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
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ChangeTimerColorButton("change_color/stopwatch", vm.haloColor)
      Spacer(Modifier.width(40.dp))
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