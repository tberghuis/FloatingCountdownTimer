package xyz.tberghuis.floatingtimer.stopwatch

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.os.Build
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.common.OverlayState
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.composables.StopwatchClickTarget
import xyz.tberghuis.floatingtimer.stopwatch.composables.StopwatchOverlay

// todo inherit interface OverlayComponent { exitOverlay, startOverlay }

class StopwatchOverlayComponent(
  val context: Context,
// val stopService: () -> Unit
) {
  val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val density = context.resources.displayMetrics.density
  val timerSizePx = (TIMER_SIZE_DP * density).toInt()

  val fullscreenOverlay: OverlayViewHolder
  val clickTargetOverlay: OverlayViewHolder

  val overlayState = OverlayState()
  var isOverlayShowing = false

  init {
    fullscreenOverlay = initFullscreenOverlay()
    clickTargetOverlay = initClickTargetOverlay()
  }

  private fun initClickTargetOverlay(): OverlayViewHolder {
    val clickTargetOverlay = OverlayViewHolder(
      WindowManager.LayoutParams(
        timerSizePx,
        timerSizePx,
        0, // todo place default position
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), context
    )
    clickTargetOverlay.view.setContent {
      StopwatchClickTarget(overlayState)
    }

    return clickTargetOverlay
  }

  private fun initFullscreenOverlay(): OverlayViewHolder {
    var fullscreenOverlay = OverlayViewHolder(
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), context
    )
    fullscreenOverlay.params.alpha = 1f
    val inputManager = context.getSystemService(INPUT_SERVICE) as InputManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
      fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
    }
    fullscreenOverlay.view.setContent {
      StopwatchOverlay(overlayState)
    }
    return fullscreenOverlay
  }

  fun showOverlays() {
    if(isOverlayShowing){
      return
    }
    isOverlayShowing = true
    stopwatchState.resetStopwatchState()
    windowManager.addView(clickTargetOverlay.view, clickTargetOverlay.params)
    windowManager.addView(fullscreenOverlay.view, fullscreenOverlay.params)
  }

  fun removeOverlays() {
    logd("StopwatchOverlayComponent removeOverlays")
    windowManager.removeView(clickTargetOverlay.view)
    windowManager.removeView(fullscreenOverlay.view)

    clickTargetOverlay.view.disposeComposition()
    fullscreenOverlay.view.disposeComposition()

    isOverlayShowing = false
  }
}