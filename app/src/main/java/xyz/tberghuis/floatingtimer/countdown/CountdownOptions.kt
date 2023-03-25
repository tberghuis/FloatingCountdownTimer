package xyz.tberghuis.floatingtimer.countdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CountdownOptions() {

  Column {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = true,
        onCheckedChange = {

        }
      )
      Text("Vibration")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = true,
        onCheckedChange = {

        }
      )
      Text("Sound")
    }
  }
}