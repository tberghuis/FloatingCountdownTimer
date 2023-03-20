package xyz.tberghuis.floatingtimer.service.countdown

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.min
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.collectLatest
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.composables.ProgressArc
import xyz.tberghuis.floatingtimer.composables.Trash
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.service.OverlayController
import xyz.tberghuis.floatingtimer.service.ServiceState

@Composable
fun CountdownOverlay(state: ServiceState) {
  val countdownState = state.countdownState
  val overlayState = countdownState.overlayState
  // should i use derivedStateOf ???
  // i don't understand the benefit
  val timeLeftFraction = countdownState.countdownSeconds / countdownState.durationSeconds.toFloat()
  val context = LocalContext.current

  Box(Modifier.onGloballyPositioned {
    // do this instead of service onConfigurationChanged
    // to reposition timer when screen rotate
    val density = context.resources.displayMetrics.density
    val timerSizePx = (TIMER_SIZE_DP * density).toInt()
    val x = min(overlayState.timerOffset.x, state.screenWidthPx - timerSizePx)
    val y = min(overlayState.timerOffset.y, state.screenHeightPx - timerSizePx)
    overlayState.timerOffset = IntOffset(x, y)
  }) {
    Box(modifier = Modifier
      .offset {
        overlayState.timerOffset
      }
      .size(TIMER_SIZE_DP.dp)
      .padding(PROGRESS_ARC_WIDTH / 2)
      .zIndex(1f),
      contentAlignment = Alignment.Center) {
      ProgressArc(timeLeftFraction)
      TimeDisplay(countdownState.countdownSeconds)
    }

    if (overlayState.showTrash) {
      // don't really need to nest this column
      // doing for positioning simplicity
      // refactor later
      Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Trash(overlayState)
      }
    }
  }

  // todo move into AlarmController
  LaunchedEffect(Unit) {
    logd("CountdownOverlay LaunchedEffect")
    var countDownTimer: CountDownTimer? = null
    countdownState.timerState.collectLatest {
      countDownTimer?.cancel()
      when (it) {
        is TimerStateRunning -> {
          // todo make timer more accurate
          // when pause store countdownMillis
          countDownTimer = object : CountDownTimer(countdownState.countdownSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
              countdownState.countdownSeconds = (millisUntilFinished / 1000f).roundToInt()
            }

            override fun onFinish() {
              countdownState.countdownSeconds = 0
            }
          }.start()
        }
        is TimerStatePaused -> {
          // do nothing
        }
        else -> {}
      }
    }
  }
}