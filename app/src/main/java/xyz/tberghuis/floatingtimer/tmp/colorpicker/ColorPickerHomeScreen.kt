package xyz.tberghuis.floatingtimer.tmp.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ColorPickerHomeScreen(
  nav: () -> Unit
) {
  Column {
    Text("color picker home screen")
    Button(onClick = {
      nav()
    }) {
      Text("change color")
    }
  }
}