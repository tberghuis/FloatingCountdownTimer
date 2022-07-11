package xyz.tberghuis.floatingtimer.stopwatch.composables

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_CREATE_STOPWATCH
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchService
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel

@Composable
fun CreateStopwatchCard() {
  val context = LocalContext.current
  val vm: HomeViewModel = hiltViewModel()
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
    elevation = 10.dp
  ) {
    Column(
      modifier = Modifier
        .padding(10.dp)
//        .background(Color.Green)
      ,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Stopwatch", fontSize = 20.sp)

      Button(
        modifier = Modifier.padding(top = 10.dp),
        onClick = {
          logd("start stopwatch")
          if (!Settings.canDrawOverlays(context)) {
            vm.showGrantOverlayDialog = true
            return@Button
          }
          startStopwatchService(context)
        }) {
        Text("Create")
      }
    }
  }
}

fun startStopwatchService(context: Context) {
  val intent = Intent(context.applicationContext, StopwatchService::class.java)
  intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_CREATE_STOPWATCH)
  context.startForegroundService(intent)
}