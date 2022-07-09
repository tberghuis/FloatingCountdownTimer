package xyz.tberghuis.floatingtimer.stopwatch

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.os.Build
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.stopwatch.StopwatchServiceOverlay

class StopwatchOverlayComponent(val context: Context, val stopService: () -> Unit) {
  val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  val fullscreenOverlay: OverlayViewHolder


  init {
    fullscreenOverlay = initFullscreenOverlay()
  }

  fun initFullscreenOverlay(): OverlayViewHolder {
    var fullscreenOverlay = OverlayViewHolder(
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
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
      StopwatchServiceOverlay({ this.exit() })
    }


    return fullscreenOverlay
  }

  fun exit() {
    logd("exit overlay component")
    windowManager.removeView(fullscreenOverlay.view)
    stopService()
  }

  fun showOverlay() {
    windowManager.addView(fullscreenOverlay.view, fullscreenOverlay.params)
  }
}