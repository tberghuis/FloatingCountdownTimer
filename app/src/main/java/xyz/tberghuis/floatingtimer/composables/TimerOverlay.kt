package xyz.tberghuis.floatingtimer.composables

import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.collectLatest
import xyz.tberghuis.floatingtimer.OverlayStateHolder.countdownSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.durationSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.screenHeightPx
import xyz.tberghuis.floatingtimer.OverlayStateHolder.screenWidthPx
import xyz.tberghuis.floatingtimer.OverlayStateHolder.showTrash
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerOffset
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerState
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.TimerStateFinished
import xyz.tberghuis.floatingtimer.TimerStatePaused
import xyz.tberghuis.floatingtimer.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.min
import kotlin.math.roundToInt

// todo remove player reference
// use class AlarmComponent??? contains player, deals with AlarmManager etc
@Composable
fun TimerOverlay(player: MediaPlayer) {

  // should i use derivedStateOf ???
  // i don't understand the benefit
  val timeLeftFraction = countdownSeconds / durationSeconds.toFloat()

  val context = LocalContext.current

  Box(Modifier.onGloballyPositioned {
    logd("TimerOverlay onGloballyPositioned")
    screenWidthPx = it.size.width
    screenHeightPx = it.size.height

    // do this instead of service onConfigurationChanged
    // to reposition timer when screen rotate
    val density = context.resources.displayMetrics.density
    val timerSizePx = (TIMER_SIZE_DP * density).toInt()
    val x = min(timerOffset.x, screenWidthPx - timerSizePx)
    val y = min(timerOffset.y, screenHeightPx - timerSizePx)
    timerOffset = IntOffset(x, y)
  }) {
    Box(
      modifier = Modifier
        .offset {
          timerOffset
        }
//        .background(Color.Red)
        .size(TIMER_SIZE_DP.dp)
        .padding(PROGRESS_ARC_WIDTH / 2)
        .zIndex(1f)
      ,
      contentAlignment = Alignment.Center
    ) {

      ProgressArc(timeLeftFraction)
      TimerDisplay()
    }

    if (showTrash) {
      // don't really need to nest this column
      // doing for positioning simplicity
      // refactor later
      Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Trash()
      }
    }
  }

  // i should move this code into AlarmController
  // that lives in service
  LaunchedEffect(Unit) {
    var countDownTimer: CountDownTimer? = null
    timerState.collectLatest {
      countDownTimer?.cancel()
      when (it) {
        is TimerStateRunning -> {
          // todo make timer more accurate
          // when pause store countdownMillis
          countDownTimer = object : CountDownTimer(countdownSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
              countdownSeconds = (millisUntilFinished / 1000f).roundToInt()
            }

            override fun onFinish() {
              countdownSeconds = 0
              // todo no i need to do this???
//              timerState.value = TimerStateFinished
            }
          }.start()
        }
        is TimerStatePaused -> {
          // do nothing
        }
        is TimerStateFinished -> {
          // play alarm
          player.start()
          // vibrate
        }
      }
    }
  }
}

@Composable
fun ProgressArc(timeLeftFraction: Float) {
  val sweepAngle = 360 * timeLeftFraction

  Canvas(
    Modifier
      .fillMaxSize()
  ) {
    // background
    // todo make partial transparent
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
      color = Color.Blue.copy(alpha = .1f),
      startAngle = 0f,
      sweepAngle = 360f,
      useCenter = false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )

    drawArc(
      color = Color.Blue,
      -90f,
      sweepAngle,
      false,
      style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
      size = Size(size.width, size.height)
    )
  }
}