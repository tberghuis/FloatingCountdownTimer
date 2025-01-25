package xyz.tberghuis.floatingtimer.service.countdown

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpBubble as Bubble
import xyz.tberghuis.floatingtimer.service.FloatingService
import kotlin.math.roundToInt

class Countdown(
  private val service: FloatingService,
  val durationSeconds: Int,
  bubbleSizeScaleFactor: Float,
  haloColor: Color,
  timerShape: String,
  label: String?,
  isBackgroundTransparent: Boolean,
  savedTimer: SavedTimer? = null
) : Bubble(
  service,
  bubbleSizeScaleFactor,
  haloColor,
  timerShape,
  label,
  isBackgroundTransparent,
  savedTimer
) {
  var countdownSeconds by mutableIntStateOf(durationSeconds)
  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)
  private var countDownTimer: CountDownTimer? = null

  override fun exit() {
    service.alarmController.stopAlarm(this)
    countDownTimer?.cancel()
    super.exit()
  }

  override fun reset() {
    countdownSeconds = durationSeconds
    timerState.value = TimerStatePaused
  }

  override fun onTap() {
    logd("click target onclick")
    when (timerState.value) {
      is TimerStatePaused -> {
        // on first tap update saved position
        if (countdownSeconds == durationSeconds) {
          saveTimerPositionIfNull()
        }
        timerState.value = TimerStateRunning
      }

      is TimerStateRunning -> {
        timerState.value = TimerStatePaused
      }

      is TimerStateFinished -> {
        reset()
      }
    }
  }

  init {
    manageAlarm()
    manageCountdownTimer()
  }

  private fun manageAlarm() {
    service.scope.launch {
      timerState.collectLatest {
        logd("timerState collectLatest $it")
        when (it) {
          TimerStateFinished -> {
            service.alarmController.startAlarm(this@Countdown)
          }

          TimerStateRunning -> {
          }

          TimerStatePaused -> {
            service.alarmController.stopAlarm(this@Countdown)
          }
        }
      }
    }
  }

  private fun manageCountdownTimer() {
    logd("manageCountdownTimer")
    // Main + immediate??? prevent ANRs???
    service.scope.launch(Dispatchers.Main) {

      timerState.collectLatest {
        countDownTimer?.cancel()
        if (it is TimerStateRunning) {
          // todo make timer more accurate
          // when pause store countdownMillis
          countDownTimer =
            object : CountDownTimer(countdownSeconds * 1000L, 1000) {
              override fun onTick(millisUntilFinished: Long) {
                countdownSeconds = (millisUntilFinished / 1000f).roundToInt()
              }

              override fun onFinish() {
                countdownSeconds = 0
                timerState.value = TimerStateFinished
              }
            }.start()
        }
      }
    }
  }
}