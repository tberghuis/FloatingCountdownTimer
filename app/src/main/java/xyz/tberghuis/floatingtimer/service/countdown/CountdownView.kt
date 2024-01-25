package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.BubbleProperties
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

@Composable
fun CountdownView(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  CountdownViewDisplay(countdown, timeLeftFraction, countdown.countdownSeconds)
}

// need better naming conventions
@Composable
fun CountdownViewDisplay(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int
) {
  Box(
    modifier = Modifier
      .size(bubbleProperties.bubbleSizeDp)
      .padding(bubbleProperties.arcWidth / 2)
      .zIndex(1f),
    contentAlignment = Alignment.Center
  ) {
    CountdownProgressArc(timeLeftFraction, bubbleProperties.arcWidth, bubbleProperties.haloColor)
    TimeDisplay(countdownSeconds, bubbleProperties.fontSize, Color(0xFF888888))
  }
}

@Composable
fun CountdownProgressArc(timeLeftFraction: Float, arcWidth: Dp, haloColor: Color) {
  val sweepAngle = 360 * timeLeftFraction

  Canvas(
    Modifier.fillMaxSize()
  ) {
    // background
    // todo make partial transparent
    drawCircle(
      color = Color.White,
    )
    drawArc(
      color = Color.White,
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(arcWidth.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = haloColor.copy(alpha = .1f),
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(arcWidth.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = haloColor,
      -90f,
      sweepAngle,
      false,
      style = Stroke(arcWidth.toPx()),
      size = Size(size.width, size.height)
    )
  }
}