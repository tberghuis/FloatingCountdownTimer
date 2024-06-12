package xyz.tberghuis.floatingtimer.tmp7

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.countdown.Countdown

class TmpFtAlarmController(
  floatingService: FloatingService
) {
  private val ringtone: MutableStateFlow<Ringtone?> = MutableStateFlow(null)
  private val finishedCountdowns = MutableStateFlow(setOf<Countdown>())

  init {
    val prefs = floatingService.application.providePreferencesRepository()
    floatingService.scope.launch {
      prefs.alarmRingtoneUriFlow.collect { uri ->
        uri?.let {
          ringtone.value?.stop()
          ringtone.value =
            RingtoneManager.getRingtone(floatingService.application, Uri.parse(uri))?.apply {
              audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setLegacyStreamType(AudioManager.STREAM_ALARM)
                .build()
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isHapticGeneratorEnabled = false
              }
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                isLooping = true
              }
            }
        }
      }
    }

    floatingService.scope.launch {
      try {
        finishedCountdowns.collect {
          withContext(Main.immediate) {
            when (it.size) {
              0 -> {
                if (ringtone.value?.isPlaying == true) {
                  ringtone.value?.stop()
                }
              }
              // allow for single play ringtone on older apilevels < 28
              else -> {
                if (ringtone.value?.isPlaying == false) {
                  ringtone.value?.play()
                }
              }
            }
          }
        }
      } finally {
        // scope is cancelled
        // lesson, don't call withContext in finally block
        if (ringtone.value?.isPlaying == true) {
          ringtone.value?.stop()
        }
      }
    }
  }

  fun startAlarm(c: Countdown) {
    finishedCountdowns.value += c
  }

  fun stopAlarm(c: Countdown) {
    finishedCountdowns.value -= c
  }
}