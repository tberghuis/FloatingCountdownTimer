package xyz.tberghuis.floatingtimer.service.countdown

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import xyz.tberghuis.floatingtimer.composables.LocalTimerViewHolder
import xyz.tberghuis.floatingtimer.composables.TimerRectView
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp5.TmpCountdownView
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
  TmpCountdownView(countdown, timeLeftFraction, countdown.countdownSeconds, isPaused)
}

@Composable
fun CountdownView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {
  TmpCountdownView(
    bubbleProperties,
    timeLeftFraction,
    countdownSeconds,
    isPaused
  )
}

@Composable
fun XxxCountdownView(
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
      val tvh = LocalTimerViewHolder.current
      val updateViewLayout = tvh?.let {
        { size: IntSize ->
          logd("runOnceOnGloballyPositioned $size")
          tvh.params.width = size.width
          tvh.params.height = size.height
          tvh.service.ftWindowManager.updateViewLayout(tvh.view, tvh.params)
        }
      }
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
        updateViewLayout
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}