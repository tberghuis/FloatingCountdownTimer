package xyz.tberghuis.floatingtimer.stopwatch.composables

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import kotlinx.coroutines.flow.collect
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.common.OverlayState
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.composables.Trash
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.min
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchState

@Composable
fun StopwatchOverlay(overlayState: OverlayState, stopwatchState: StopwatchState) {
  val timerSizePx = LocalDensity.current.run { TIMER_SIZE_DP.dp.toPx() }.toInt()

  Box(
    Modifier.onGloballyPositioned {
//      logd("TimerOverlay onGloballyPositioned")
      overlayState.screenWidthPx = it.size.width
      overlayState.screenHeightPx = it.size.height
      val x = min(overlayState.timerOffset.x, overlayState.screenWidthPx - timerSizePx)
      val y = min(overlayState.timerOffset.y, overlayState.screenHeightPx - timerSizePx)
      overlayState.timerOffset = IntOffset(x, y)
    }
  ) {
    Box(
      modifier = Modifier
        .offset {
          overlayState.timerOffset
        }
        .size(TIMER_SIZE_DP.dp)
//        .background(Color.Yellow)
        .padding(PROGRESS_ARC_WIDTH / 2),
      contentAlignment = Alignment.Center
    ) {
      BorderArc(stopwatchState)
      TimeDisplay(stopwatchState.timeElapsed.value)
    }

    if (overlayState.showTrash) {
      Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Trash(overlayState)
      }
    }
  }
}

@Composable
fun BorderArc(stopwatchState: StopwatchState) {
  var pausedAngle by remember { mutableStateOf(210f) }
  var restartAngle by remember { mutableStateOf(0f) }
  val infiniteTransition = rememberInfiniteTransition()
  val primaryColor = MaterialTheme.colorScheme.primary
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
      color = Color.White,
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = primaryColor.copy(alpha = .1f),
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = primaryColor,
      startAngle = if (!stopwatchState.running.value) pausedAngle else drawAnimatedAngle,
      sweepAngle = 120f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )
  }

  // should learn to write my own delegate for angle instead
  // doitwrong
  LaunchedEffect(Unit) {
    snapshotFlow { stopwatchState.running.value }
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