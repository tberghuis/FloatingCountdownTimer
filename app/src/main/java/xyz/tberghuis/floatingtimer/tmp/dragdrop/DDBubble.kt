package xyz.tberghuis.floatingtimer.tmp.dragdrop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DDBubble() {
  Box() {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)
    ) {
      Text("0:00")
    }
  }
}