package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.derivedStateOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.tberghuis.floatingtimer.tmp7.StopwatchCircleView
import xyz.tberghuis.floatingtimer.tmp7.TimerRectView

@Composable
fun StopwatchView(
  stopwatch: Stopwatch
) {
  StopwatchView(
    isRunningStateFlow = stopwatch.isRunningStateFlow,
    widthDp = stopwatch.widthDp,
    heightDp = stopwatch.heightDp,
    arcWidth = stopwatch.arcWidth,
    haloColor = stopwatch.haloColor,
    timeElapsed = stopwatch.timeElapsed.intValue,
    fontSize = stopwatch.fontSize,
    timerShape = stopwatch.timerShape,
  )
}

@Composable
fun StopwatchView(
  // when isRunningStateFlow null, showing preview in saved timers card
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  widthDp: Dp,
  heightDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
  timerShape: String
) {
  when (timerShape) {
    "circle" -> {
      StopwatchCircleView(
        isRunningStateFlow = isRunningStateFlow,
        bubbleSizeDp = widthDp,
        arcWidth = arcWidth,
        haloColor = haloColor,
        timeElapsed = timeElapsed,
        fontSize = fontSize
      )
    }

    "rectangle" -> {
      val isPaused = isRunningStateFlow?.collectAsState()?.value?.not() ?: false
      TimerRectView(
        isPaused = isPaused,
        widthDp = widthDp,
        heightDp = heightDp,
        arcWidth = arcWidth,
        haloColor = haloColor,
        timeElapsed = timeElapsed,
        timeLeftFraction = 1f,
        fontSize = fontSize,
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}

@Composable
fun StopwatchBorderArc(
  isRunningStateFlow: StateFlow<Boolean>?,
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

  val running = isRunningStateFlow?.collectAsState()?.value ?: false

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