package xyz.tberghuis.floatingtimer.stopwatch

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.InternalCoroutinesApi
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.logd
import kotlinx.coroutines.flow.collect

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
  var pausedAngle by remember { mutableStateOf(210f) }
  var restartAngle by remember { mutableStateOf(0f) }
  val infiniteTransition = rememberInfiniteTransition()
  val animatedAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(3000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
  val drawAnimatedAngle by remember {
    derivedStateOf { pausedAngle + animatedAngle - restartAngle }
  }

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
      startAngle = if (!StopwatchStateHolder.running.value) pausedAngle else drawAnimatedAngle,
      sweepAngle = 120f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )
  }

  // should learn to write my own delegate for angle instead
  // doitwrong
  LaunchedEffect(Unit) {
    snapshotFlow { StopwatchStateHolder.running.value }
      .collect { running ->
        when (running) {
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