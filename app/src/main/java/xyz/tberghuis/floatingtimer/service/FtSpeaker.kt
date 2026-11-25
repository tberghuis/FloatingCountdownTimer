package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.logd
import java.util.UUID

/**
 * Speaks the time remaining on countdowns out loud, for people who can't
 * see the screen or don't have their phone in front of them.
 * Off by default. The TextToSpeech engine is only started when the setting is on.
 */
class FtSpeaker(
  private val floatingService: FloatingService
) {
  private val prefs = floatingService.application.preferencesRepository
  private val audioManager =
    floatingService.getSystemService(Context.AUDIO_SERVICE) as AudioManager

  private val audioAttributes = AudioAttributes.Builder()
    .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
    .build()

  // lower other audio (music) a little while speaking, then give it back
  private val focusRequest =
    AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
      .setAudioAttributes(audioAttributes)
      .build()

  @Volatile
  private var enabled = false

  @Volatile
  private var intervalMinutes = 1

  private val lock = Any()
  private var tts: TextToSpeech? = null
  private var ttsReady = false
  private var destroyed = false

  // bumped every start/stop so a late init callback from an old engine is ignored
  private var generation = 0

  init {
    floatingService.scope.launch {
      prefs.speakTimeFlow.distinctUntilChanged().collectLatest {
        enabled = it
        // create and shut down the engine on the main thread
        withContext(Dispatchers.Main) {
          if (it) startTts() else stopTts()
        }
      }
    }
    floatingService.scope.launch {
      prefs.speakIntervalMinutesFlow.collectLatest {
        intervalMinutes = it
      }
    }
  }

  private fun startTts(): Unit = synchronized(lock) {
    if (destroyed || tts != null) return
    val myGeneration = ++generation
    tts = TextToSpeech(floatingService.applicationContext) { status ->
      synchronized(lock) {
        if (myGeneration != generation) return@synchronized
        val engine = tts ?: return@synchronized
        if (status == TextToSpeech.SUCCESS) {
          ttsReady = true
          engine.setAudioAttributes(audioAttributes)
          engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) = abandonFocusIfIdle()

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) = abandonFocusIfIdle()
          })
        } else {
          Log.e("FtSpeaker", "TextToSpeech init failed, status $status")
          // clean up so turning the setting off and on again retries
          engine.shutdown()
          tts = null
        }
      }
    }
  }

  private fun stopTts(): Unit = synchronized(lock) {
    generation++
    tts?.stop()
    tts?.shutdown()
    tts = null
    ttsReady = false
    audioManager.abandonAudioFocusRequest(focusRequest)
  }

  private fun abandonFocusIfIdle(): Unit = synchronized(lock) {
    if (tts?.isSpeaking != true) {
      audioManager.abandonAudioFocusRequest(focusRequest)
    }
  }

  private fun speak(text: String): Unit = synchronized(lock) {
    val engine = tts
    if (!enabled || !ttsReady || engine == null) return
    logd("FtSpeaker speak: $text")
    audioManager.requestAudioFocus(focusRequest)
    val result = engine.speak(text, TextToSpeech.QUEUE_ADD, Bundle(), UUID.randomUUID().toString())
    if (result != TextToSpeech.SUCCESS) {
      audioManager.abandonAudioFocusRequest(focusRequest)
    }
  }

  private fun withLabel(label: String?, text: String): String {
    return if (label.isNullOrBlank()) text
    else floatingService.getString(R.string.tts_label_prefix, label, text)
  }

  /** Call on every countdown tick with the old and new seconds remaining. */
  fun onCountdownTick(label: String?, previousSeconds: Int, currentSeconds: Int) {
    if (!enabled) return
    val minutes = minutesToAnnounce(previousSeconds, currentSeconds, intervalMinutes) ?: return
    val text = floatingService.resources.getQuantityString(
      R.plurals.tts_minutes_remaining, minutes, minutes
    )
    speak(withLabel(label, text))
  }

  /** Call once when a countdown reaches zero. */
  fun onCountdownFinished(label: String?) {
    if (!enabled) return
    speak(withLabel(label, floatingService.getString(R.string.tts_times_up)))
  }

  fun onDestroy() {
    synchronized(lock) {
      destroyed = true
      enabled = false
    }
    stopTts()
  }
}
