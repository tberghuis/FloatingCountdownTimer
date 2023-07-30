package xyz.tberghuis.floatingtimer.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning
import kotlin.math.roundToInt

class AlarmController(val service: FloatingService) {
  private var player: MediaPlayer? = null
  private var pendingAlarm: PendingIntent? = null
  private var vibrate = true
  private val vibrator = initVibrator()
  private var sound = true

  init {
    val alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    // no default alarm sound set
    if (alarmSound != null) {
      player = MediaPlayer.create(service, alarmSound)
    }
    player?.isLooping = true
    collectPreferences()
    watchState()
    scheduleCountdownTimer()
  }

  // todo rewrite without
  //   private var vibrate, sound
  // just call .first in watchState()
  private fun collectPreferences() {
    // doitwrong
    val preferences = providePreferencesRepository(service)

    service.scope.launch {
      preferences.vibrationFlow.collect {
        vibrate = it
      }
    }
    service.scope.launch {
      preferences.soundFlow.collect {
        sound = it
      }
    }
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

  private fun watchState() {
    val context = service
    val countdownState = service.state.countdownState

    service.scope.launch {
      countdownState.timerState.collectLatest {
        when (it) {
          TimerStateFinished -> {
            logd("does the player start")
            pendingAlarm?.cancel()
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
            // set alarm
            logd("todo: run the timer")
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            // todo do i need to save pendingAlarm to state???
            pendingAlarm = PendingIntent.getBroadcast(
              context.applicationContext,
              REQUEST_CODE_PENDING_ALARM,
              intent,
              PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            try {
              alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                  System.currentTimeMillis() + (countdownState.countdownSeconds * 1000),
                  pendingAlarm
                ), pendingAlarm
              )
            } catch (e: SecurityException) {
              Log.e("AlarmController", "SecurityException: $e")
            }
          }

          TimerStatePaused -> {
            pendingAlarm?.cancel()
            if (player?.isPlaying == true) {
              player?.pause()
            }
            vibrator.cancel()
          }
        }
      }
    }
  }

  private fun scheduleCountdownTimer() {
    logd("scheduleCountdownTimer")
    val countdownState = service.state.countdownState
    service.scope.launch(Dispatchers.Main) {
      var countDownTimer: CountDownTimer? = null
      countdownState.timerState.collectLatest {
        countDownTimer?.cancel()
        if (it is TimerStateRunning) {
          // todo make timer more accurate
          // when pause store countdownMillis
          countDownTimer =
            object : CountDownTimer(countdownState.countdownSeconds * 1000L, 1000) {
              override fun onTick(millisUntilFinished: Long) {
                countdownState.countdownSeconds = (millisUntilFinished / 1000f).roundToInt()
              }

              override fun onFinish() {
                countdownState.countdownSeconds = 0
              }
            }.start()
        }
      }
    }
  }
}