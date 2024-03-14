package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import xyz.tberghuis.floatingtimer.tmp7.TmpCountdownView

@Composable
fun CountdownView(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  val timerState = countdown.timerState.collectAsState()
  val isPaused by remember {
    derivedStateOf {
      timerState.value == TimerStatePaused
    }
  }
  TmpCountdownView(countdown, timeLeftFraction, countdown.countdownSeconds, isPaused)
}

//@Composable
//fun CountdownViewDisplay(
//  bubbleProperties: BubbleProperties,
//  timeLeftFraction: Float,
//  countdownSeconds: Int,
//  isPaused: Boolean
//) {
//  Box(
//    modifier = Modifier
//      // todo check everywhere widthDp or widthPx is referenced, implement when(timerShape)
//      .size(bubbleProperties.widthDp)
//      .padding(bubbleProperties.arcWidth / 2)
//      .zIndex(1f),
//    contentAlignment = Alignment.Center
//  ) {
//    CountdownProgressArc(timeLeftFraction, bubbleProperties.arcWidth, bubbleProperties.haloColor)
//    if (isPaused) {
//      Icon(
//        Icons.Filled.PlayArrow,
//        contentDescription = "paused",
//        modifier = Modifier.fillMaxSize(),
//        tint = Color.LightGray
//      )
//    }
//    TimeDisplay(countdownSeconds, bubbleProperties.fontSize)
//  }
//}


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