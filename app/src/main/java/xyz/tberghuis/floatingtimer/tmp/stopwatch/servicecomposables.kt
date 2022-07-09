package xyz.tberghuis.floatingtimer.tmp.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchStateHolder as state

@Composable
fun StopwatchServiceOverlay(exit: () -> Unit) {

  Column(verticalArrangement = Arrangement.Center) {
    Text("hello stopwatch overlay")
    Text(state.willitblend)
    Button(onClick = {
      logd("exit composable")
      exit()
    }) {
      Text("exit")
    }
  }
}