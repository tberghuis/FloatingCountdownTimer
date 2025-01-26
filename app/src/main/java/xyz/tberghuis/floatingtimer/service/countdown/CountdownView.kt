package xyz.tberghuis.floatingtimer.service.countdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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