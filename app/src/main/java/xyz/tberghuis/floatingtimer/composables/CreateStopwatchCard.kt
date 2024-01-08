package xyz.tberghuis.floatingtimer.composables

import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel

@Composable
fun CreateStopwatchCard() {
  val context = LocalContext.current
  val navController = LocalNavController.current
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
      Text(stringResource(id = R.string.stopwatch), fontSize = 20.sp)
    }
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {


      Box(
        Modifier
          .size(50.dp)
          .background(vm.stopwatchHaloColor)
          .clickable {
            navController.navigate("change_color/stopwatch")
          }
      )


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