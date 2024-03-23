package xyz.tberghuis.floatingtimer.tmp5

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpStopwatchScreen(
  vm: TmpStopwatchScreenVm = viewModel()
) {
  val context = LocalContext.current

  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("stopwatch screen")
    Button(onClick = {
//      vm.createOverlay(context as ComponentActivity)
      vm.stopwatchButtonClick()
    }) {
      Text("create stopwatch with label")
    }
  }
}