package xyz.tberghuis.floatingtimer.tmp2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver
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
  private var pendingAlarm: PendingIntent? = null

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
    manageAlarm()
    manageCountdownTimer()
  }

  private fun manageAlarm() {
    val floatingAlarm = service.floatingAlarm
    service.scope.launch {
      timerState.collectLatest {
        when (it) {
          TimerStateFinished -> {
            logd("does the player start")
            pendingAlarm?.cancel()
            if (floatingAlarm.sound) {
              floatingAlarm.player?.start()
            }
            if (floatingAlarm.vibrate) {
              floatingAlarm.vibrator.vibrate(
                VibrationEffect.createWaveform(
                  longArrayOf(1500, 200), intArrayOf(255, 0), 0
                )
              )
            }
          }

          TimerStateRunning -> {
            // set alarm
            logd("todo: run the timer")
            val intent = Intent(service, AlarmReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            // todo do i need to save pendingAlarm to state???
            pendingAlarm = PendingIntent.getBroadcast(
              service.applicationContext,
              REQUEST_CODE_PENDING_ALARM,
              intent,
              PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = service.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            try {
              alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                  System.currentTimeMillis() + (countdownSeconds * 1000),
                  pendingAlarm
                ), pendingAlarm!!
              )
            } catch (e: SecurityException) {
              Log.e("Countdown", "SecurityException: $e")
            }
          }

          TimerStatePaused -> {
            pendingAlarm?.cancel()
            if (floatingAlarm.player?.isPlaying == true) {
              floatingAlarm.player?.pause()
            }
            floatingAlarm.vibrator.cancel()
          }
        }
      }
    }
  }

  private fun manageCountdownTimer() {
    logd("manageCountdownTimer")
    // Main + immediate??? prevent ANRs???
    service.scope.launch(Dispatchers.Main) {
      var countDownTimer: CountDownTimer? = null
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
              }
            }.start()
        }
      }
    }
  }
}