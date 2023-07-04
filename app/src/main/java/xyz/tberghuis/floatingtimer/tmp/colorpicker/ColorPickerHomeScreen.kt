package xyz.tberghuis.floatingtimer.tmp.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun ColorPickerHomeScreen(
  nav: NavHostController
) {
  Column {
    Text("color picker home screen")
    Button(onClick = {
      nav.navigate("color_picker")
    }) {
      Text("change color")
    }
    Button(onClick = {
      nav.navigate("godaddy_picker")
    }) {
      Text("godaddy")
    }
  }
}