package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun VibrationDemo() {
  Column {
    Text("hello vibration")

    Button(onClick = {}) {
      Text("start vibration")
    }

    Button(onClick = {}) {
      Text("stop vibration")
    }

  }
}