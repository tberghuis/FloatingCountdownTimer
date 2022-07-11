package xyz.tberghuis.floatingtimer.stopwatch.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun StopwatchClickTarget() {
  Box(
    modifier = Modifier
      .background(Color.Red)
  ) {
    Text("hello click target")
  }

}