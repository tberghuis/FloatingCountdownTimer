package xyz.tberghuis.floatingtimer.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.material.CircularProgressIndicator

@Composable
fun StopwatchOverlay() {
  Box(
    modifier = Modifier
      // todo use size from constants
      .size(100.dp)
      .background(Color.Yellow),
    contentAlignment = Alignment.Center
  ) {
//    Text("hello overlay")
//    BorderArc()
  }
}

@Composable
fun BorderArc() {
  Canvas(
    Modifier
      .fillMaxSize()
  ) {
    drawCircle(
      color = Color.White,
    )
  }
}


