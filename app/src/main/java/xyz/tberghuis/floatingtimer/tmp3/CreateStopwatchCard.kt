package xyz.tberghuis.floatingtimer.tmp3

import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd

@Composable
fun CreateStopwatchCard() {
  val context = LocalContext.current
  val vm: HomeViewModel = viewModel()
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
      Text(stringResource(id = R.string.stopwatch) , fontSize = 20.sp)
    }
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
      Button(modifier = Modifier.padding(top = 10.dp), onClick = {
        logd("start stopwatch")
        if (!Settings.canDrawOverlays(context)) {
          vm.showGrantOverlayDialog = true
          return@Button
        }
        vm.stopwatchButtonClick()
      }) {
        Text(stringResource(id = R.string.create))
      }
    }
  }
}