package xyz.tberghuis.floatingtimer.stopwatch.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_CREATE_STOPWATCH
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchService

@Composable
fun CreateStopwatchCard() {
  val context = LocalContext.current
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
    elevation = 10.dp
  ) {
    Column(
      modifier = Modifier
        .background(Color.Green)
    ) {
      Text("Stopwatch")
      Button(onClick = {
        logd("start stopwatch")
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