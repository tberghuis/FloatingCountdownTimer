package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import xyz.tberghuis.floatingtimer.composables.TimerRectView
import xyz.tberghuis.floatingtimer.tmp4.TmpBubbleProperties as BubbleProperties

@Composable
fun CountdownView(countdown: Countdown) {
  val timeLeftFraction = countdown.countdownSeconds / countdown.durationSeconds.toFloat()
  val timerState = countdown.timerState.collectAsState()
  val isPaused by remember {
    derivedStateOf {
      timerState.value == TimerStatePaused
    }
  }
  CountdownView(countdown, timeLeftFraction, countdown.countdownSeconds, isPaused)
}

@Composable
fun CountdownView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {
  when (bubbleProperties.timerShape) {
    "circle" -> {
      CountdownCircleView(
        bubbleProperties = bubbleProperties,
        timeLeftFraction = timeLeftFraction,
        countdownSeconds = countdownSeconds,
        isPaused = isPaused,
        bubbleProperties.isBackgroundTransparent
      )
    }

    "label", "rectangle" -> {
      // this is redundant, unless bad data in DB
      val label = if (bubbleProperties.timerShape == "label") bubbleProperties.label else null
      TimerRectView(
        isPaused,
        bubbleProperties.arcWidth,
        bubbleProperties.haloColor,
        countdownSeconds,
        timeLeftFraction,
        bubbleProperties.fontSize,
        label,
        bubbleProperties.isBackgroundTransparent,
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}