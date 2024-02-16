package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.derivedStateOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StopwatchView(
  stopwatch: Stopwatch
) {
  StopwatchView(
    isRunningStateFlow = stopwatch.isRunningStateFlow,
    bubbleSizeDp = stopwatch.bubbleSizeDp,
    arcWidth = stopwatch.arcWidth,
    haloColor = stopwatch.haloColor,
    timeElapsed = stopwatch.timeElapsed,
    fontSize = stopwatch.fontSize
  )
}

@Composable
fun StopwatchView(
  isRunningStateFlow: MutableStateFlow<Boolean>,
  bubbleSizeDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: MutableIntState,
  fontSize: TextUnit
) {
  val isRunning = isRunningStateFlow.collectAsState()
  val isPaused by remember {
    derivedStateOf {
      !isRunning.value
    }
  }
  Box(
    modifier = Modifier
      .size(bubbleSizeDp)
      .padding(arcWidth / 2),
    contentAlignment = Alignment.Center
  ) {
    StopwatchBorderArc(isRunningStateFlow, arcWidth, haloColor)
    if (isPaused) {
      Icon(
        Icons.Filled.PlayArrow,
        contentDescription = "paused",
        modifier = Modifier.fillMaxSize(),
        tint = Color.LightGray
      )
    }
    TimeDisplay(timeElapsed.intValue, fontSize)
  }
}

@Composable
fun StopwatchBorderArc(
  isRunningStateFlow: StateFlow<Boolean>,
  arcWidth: Dp,
  haloColor: Color
) {
  var pausedAngle by remember { mutableFloatStateOf(210f) }
  var restartAngle by remember { mutableFloatStateOf(0f) }
  val infiniteTransition = rememberInfiniteTransition()

  val animatedAngle by infiniteTransition.animateFloat(
    initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
      animation = tween(3000, easing = LinearEasing), repeatMode = RepeatMode.Restart
    )
  )
  val drawAnimatedAngle by remember {
    derivedStateOf { pausedAngle + animatedAngle - restartAngle }
  }

  val running = isRunningStateFlow.collectAsState()

  Canvas(
    Modifier.fillMaxSize()
  ) {
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
      startAngle = if (!running.value) pausedAngle else drawAnimatedAngle,
      sweepAngle = 120f,
      useCenter = false,
      style = Stroke(arcWidth.toPx()),
      size = Size(size.width, size.height)
    )
  }

  // should learn to write my own delegate for angle instead
  LaunchedEffect(Unit) {
    isRunningStateFlow.collect {
      when (it) {
        true -> {
          restartAngle = animatedAngle
        }

        false -> {
          pausedAngle = drawAnimatedAngle
        }
      }
    }
  }
}