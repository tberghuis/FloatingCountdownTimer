package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchService


@Composable
fun StopwatchDemo() {

  val context = LocalContext.current

  Column {
    Text("hello stopwatch")
    Button(onClick = {
      logd("start stopwatch")
      startStopwatchService(context)

    }) {
      Text("start stopwatch")
    }

    Button(onClick = {
      logd("button")
    }) {
      Text("button")
    }

  }

}


fun startStopwatchService(context: Context) {
  val intent = Intent(context.applicationContext, StopwatchService::class.java)
  context.startForegroundService(intent)
}