package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import xyz.tberghuis.floatingtimer.tmp4.TmpBubbleProperties as BubbleProperties
import xyz.tberghuis.floatingtimer.tmp4.TmpCountdownCircleView

@Composable
fun CountdownCircleView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean,
  isBackgroundTransparent: Boolean
) {
  TmpCountdownCircleView(
    bubbleProperties,
    timeLeftFraction,
    countdownSeconds,
    isPaused,
    isBackgroundTransparent
  )
}

@Composable
fun CountdownProgressArc(
  timeLeftFraction: Float, arcWidth: Dp, haloColor: Color,
  isBackgroundTransparent: Boolean
) {
  val sweepAngle = 360 * timeLeftFraction
  Canvas(
    Modifier.fillMaxSize()
  ) {
    if (!isBackgroundTransparent) {
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
    }
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