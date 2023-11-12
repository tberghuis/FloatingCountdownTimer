package xyz.tberghuis.floatingtimer.tmp2

import android.app.PendingIntent
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class FloatingAlarm(val service: FloatingService) {
  var player: MediaPlayer? = null
  //  private var pendingAlarm: PendingIntent? = null

  var vibrate = true
  val vibrator = initVibrator()
  var sound = true

  init {
    val alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    // no default alarm sound set
    if (alarmSound != null) {
      player = MediaPlayer.create(service, alarmSound)
    }
    player?.isLooping = true
    collectPreferences()
//    watchState()
//    scheduleCountdownTimer()
  }

  // todo rewrite without
  //   private var vibrate, sound
  // just call .first in watchState()
  private fun collectPreferences() {
    // doitwrong
    val preferences = service.application.providePreferencesRepository()

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
}