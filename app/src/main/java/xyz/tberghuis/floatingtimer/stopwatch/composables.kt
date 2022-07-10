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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH

@Composable
fun StopwatchOverlay() {
  Box(
    modifier = Modifier
      .size(100.dp)
      .background(Color.Yellow)
      .padding(PROGRESS_ARC_WIDTH / 2),
    contentAlignment = Alignment.Center
  ) {
    BorderArc()
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
    drawArc(
      color = Color.Blue.copy(alpha = .1f),
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = Color.Blue,
      startAngle = 0f,
      sweepAngle = 120f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )


  }
}


