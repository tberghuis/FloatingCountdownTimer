package xyz.tberghuis.floatingtimer.tmp4

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.countdown.Countdown

class TmpFtAlarmController(
  private val floatingService: FloatingService
) {
  // shortcuts
  private val prefs = floatingService.application.providePreferencesRepository()


  private var ringtone: Ringtone? = null
  private val finishedCountdowns = MutableStateFlow(setOf<Countdown>())

  // todo
  private val vibrator = initVibrator()

  private val alarmRunning = MutableStateFlow(false)

  // looping : Boolean? = null
  var looping: Boolean? = false

  // vibrate : Boolean? = null
  var vibrate: Boolean? = false
  var sound: Boolean? = true
  var ringtoneDuration: Long? = null
//  var stopPlayerJob: Job? = null


  init {
    watchRingtoneUri()
    watchAlarmRunning()
    floatingService.scope.launch {
      try {
        finishedCountdowns.collect {
          withContext(Main.immediate) {
            when (it.size) {
              0 -> {
                if (ringtone?.isPlaying == true) {
                  ringtone?.stop()
                }
              }
              // allow for single play ringtone on older apilevels < 28
              else -> {
                if (ringtone?.isPlaying == false) {
                  ringtone?.play()
                }
              }
            }
          }
        }
      } finally {
        // scope is cancelled
        // lesson, don't call withContext in finally block
        if (ringtone?.isPlaying == true) {
          ringtone?.stop()
        }
      }
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private fun watchRingtoneUri() {
    floatingService.scope.launch {
      prefs.alarmRingtoneUriFlow.mapLatest { uriString ->
        Uri.parse(uriString)
      }.collectLatest { uri ->
        uri?.let {
          alarmRunning.value = false
          ringtoneDuration = getRingtoneDuration(floatingService, uri)
          ringtone =
            RingtoneManager.getRingtone(floatingService.application, uri)?.apply {
              audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setLegacyStreamType(AudioManager.STREAM_ALARM)
                .build()
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isHapticGeneratorEnabled = false
              }
              if (Build.VERSION.SDK_INT >= 28) {
                isLooping = true
              }
            }
        }
      }
    }
  }

  private fun initVibrator(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        floatingService.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      @Suppress("DEPRECATION") floatingService.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
  }


  fun startAlarm(c: Countdown) {
    finishedCountdowns.value += c
  }

  fun stopAlarm(c: Countdown) {
    finishedCountdowns.value -= c
  }

  private fun watchAlarmRunning() {
    floatingService.scope.launch {
      alarmRunning.collectLatest { running ->
        when (running) {
          true -> {
            if (sound == true) {
              ringtone?.play()
            }
            if (vibrate == true) {
              vibrator.vibrate(
                VibrationEffect.createWaveform(
                  longArrayOf(1500, 200), intArrayOf(255, 0), 0
                )
              )
            }
            if (looping == false && ringtoneDuration != null) {
              // don't need to launch as using collectLatest
              delay(ringtoneDuration!!)
              alarmRunning.value = false
            }
          }

          false -> {
            vibrator.cancel()
            ringtone?.stop()
          }
        }
      }
    }
  }
}

private fun getRingtoneDuration(context: Context, ringtoneUri: Uri): Long? {
  // will this sometimes throw without READ_EXTERNAL_STORAGE permission???
  var duration: Long? = null
  try {
    val mediaPlayer: MediaPlayer? = MediaPlayer.create(context, ringtoneUri)
    duration = mediaPlayer?.duration?.toLong()
    mediaPlayer?.release()
  } catch (e: Exception) {
    Log.e("FtAlarmController", "error: ${e.message}")
  }
  return duration
}