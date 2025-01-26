package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.composables.TimeDisplay
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.derivedStateOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.flow.StateFlow
import xyz.tberghuis.floatingtimer.composables.SquareBackground

@Composable
fun StopwatchCircleView(
  // when isRunningStateFlow null, showing preview in saved timers card
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean,
  paddingTimerDisplay: Dp
) {
  val isRunning = isRunningStateFlow?.collectAsState()?.value

  SquareBackground(
    modifier = Modifier.padding(arcWidth / 2),
    background = {
      Box {
        StopwatchBorderArc(
          isRunningStateFlow,
          arcWidth,
          haloColor,
          isBackgroundTransparent
        )
        if (isRunning == false) {
          Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "paused",
            modifier = Modifier.fillMaxSize(),
            tint = Color.LightGray
          )
        }
      }
    },
  ) {
    Box(
      Modifier
        .padding(paddingTimerDisplay),
      contentAlignment = Alignment.Center,
    ) {
      TimeDisplay(timeElapsed, fontSize, isBackgroundTransparent)
    }
  }
}

@Composable
fun StopwatchBorderArc(
  isRunningStateFlow: StateFlow<Boolean>?,
  arcWidth: Dp,
  haloColor: Color,
  isBackgroundTransparent: Boolean
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

  val running = isRunningStateFlow?.collectAsState()?.value ?: false

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
      startAngle = if (!running) pausedAngle else drawAnimatedAngle,
      sweepAngle = 120f,
      useCenter = false,
      style = Stroke(arcWidth.toPx()),
      size = Size(size.width, size.height)
    )
  }

  // should learn to write my own delegate for angle instead
  LaunchedEffect(Unit) {
    isRunningStateFlow?.collect {
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