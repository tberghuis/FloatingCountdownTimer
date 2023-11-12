package xyz.tberghuis.floatingtimer.tmp2

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.service.countdown.TimerState
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning
import kotlin.math.roundToInt

class Countdown(
  private val service: FloatingService,
  val durationSeconds: Int = 10
) : Bubble(service) {
  var countdownSeconds by mutableStateOf(10)
  val timerState = MutableStateFlow<TimerState>(TimerStatePaused)

  private var countDownTimer: CountDownTimer? = null
  private var player: MediaPlayer? = null
  private val vibrator = initVibrator()

  override fun exit() {
    player?.pause()
    player?.release()
    vibrator.cancel()
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
    val alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    // no default alarm sound set
    if (alarmSound != null) {
      player = MediaPlayer.create(service, alarmSound)
    }
    player?.isLooping = true
    manageAlarm()
    manageCountdownTimer()
  }

  private fun initVibrator(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        service.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      @Suppress("DEPRECATION") service.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
  }

  private fun manageAlarm() {
    val preferences = service.application.providePreferencesRepository()
    service.scope.launch {
      timerState.collectLatest {
        logd("timerState collectLatest $it")
        val vibrate = preferences.vibrationFlow.first()
        val sound = preferences.soundFlow.first()
        when (it) {
          TimerStateFinished -> {
            if (sound) {
              player?.start()
            }
            if (vibrate) {
              vibrator.vibrate(
                VibrationEffect.createWaveform(
                  longArrayOf(1500, 200), intArrayOf(255, 0), 0
                )
              )
            }
          }

          TimerStateRunning -> {
          }

          TimerStatePaused -> {
            if (player?.isPlaying == true) {
              player?.pause()
            }
            vibrator.cancel()
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