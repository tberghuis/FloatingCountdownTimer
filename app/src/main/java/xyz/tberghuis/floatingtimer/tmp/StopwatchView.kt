package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.derivedStateOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.flow.StateFlow
import xyz.tberghuis.floatingtimer.composables.LocalHaloColour

@Composable
fun TmpStopwatchBorderArc(
  isRunningStateFlow: StateFlow<Boolean>, scaleFactor: Float
) {

  val arcWidth = PROGRESS_ARC_WIDTH * (0.9f * scaleFactor + 1)

  var pausedAngle by remember { mutableStateOf(210f) }
  var restartAngle by remember { mutableStateOf(0f) }
  val infiniteTransition = rememberInfiniteTransition()
  val haloColor = LocalHaloColour.current

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