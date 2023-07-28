package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.logd


@Composable
fun MaccasScreen() {
  Column {
    Button(onClick = {
      logd("button")
    }) {
      Text("button")
    }
  }
}