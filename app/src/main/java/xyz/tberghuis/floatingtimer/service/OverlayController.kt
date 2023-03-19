package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.CountdownState

val LocalOverlayController =
  compositionLocalOf<OverlayController> { error("No OverlayController provided") }

class OverlayController(val service: FloatingService) {

  val player: MediaPlayer

  val countdownState = CountdownState()
  private val countdownIsVisible: Flow<Boolean?>
    get() = countdownState.overlayState.isVisible


  private val density = service.resources.displayMetrics.density
  private val timerSizePx = (TIMER_SIZE_DP * density).toInt()
  private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  private val countdownClickTarget = OverlayViewHolder(
    WindowManager.LayoutParams(
      timerSizePx,
      timerSizePx,
      0, // todo place default position
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ), service
  )

  val fullscreenOverlay: OverlayViewHolder = OverlayViewHolder(
    WindowManager.LayoutParams(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ), service
  )

  init {
    logd("OverlayController init")

    val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    player = MediaPlayer.create(service, alarmSound)
    player.isLooping = true


    setContentOverlayView()
    setContentClickTargets()
    watchState()
  }

  private fun setContentOverlayView() {
    fullscreenOverlay.params.alpha = 1f
    val inputManager = service.applicationContext.getSystemService(INPUT_SERVICE) as InputManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
      fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
    }

    fullscreenOverlay.view.setContent {
      CompositionLocalProvider(LocalOverlayController provides this) {
        OverlayContent()
      }
    }
  }


  private fun watchState() {
    with(CoroutineScope(Dispatchers.Default)) {
      launch {
        deriveFullscreenVisibleFlow().collect { showFullscreen ->
          addOrRemoveView(fullscreenOverlay, showFullscreen)
        }
      }

      launch {
        addOrRemoveClickTargetView(countdownIsVisible, countdownClickTarget)
      }
    }
  }

  private suspend fun addOrRemoveView(viewHolder: OverlayViewHolder, isVisible: Boolean) {
    withContext(Dispatchers.Main) {
      when (isVisible) {
        true -> {
          windowManager.addView(viewHolder.view, viewHolder.params)
        }
        false -> {
          // wrap in try catch???
          windowManager.removeView(viewHolder.view)
          viewHolder.view.disposeComposition()
        }
      }
    }
  }

  private fun deriveFullscreenVisibleFlow(): Flow<Boolean> {
    // todo
//    return incrementIsVisible.combine(decrementIsVisible) { b1, b2 ->
//      if (b1 == null && b2 == null) {
//        null
//      } else {
//        listOf(b1, b2).contains(true)
//      }
//    }.filterNotNull().distinctUntilChanged()
    return countdownIsVisible.filterNotNull().distinctUntilChanged()
  }

  private suspend fun addOrRemoveClickTargetView(
    isVisible: Flow<Boolean?>, viewHolder: OverlayViewHolder
  ) {
    isVisible.filterNotNull().collect {
      addOrRemoveView(viewHolder, it)
    }
  }


  private fun setContentClickTargets() {
    countdownClickTarget.view.setContent {
      ClickTarget {
        logd("click target onclick")
      }
    }
  }
}