package xyz.tberghuis.floatingtimer.tmp.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.logd

@Composable
fun StopwatchServiceOverlay() {

  Column(verticalArrangement = Arrangement.Center) {
    Text("hello stopwatch overlay")
    Button(onClick = {
      logd("exit")
    }) {
      Text("exit")
    }
  }
}