package xyz.tberghuis.floatingtimer.stopwatch

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.os.Build
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.IntOffset
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.common.OverlayStateFDSFSDF
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.composables.StopwatchClickTargetCDSCDS
import xyz.tberghuis.floatingtimer.stopwatch.composables.StopwatchOverlayVFDSDSC

// todo inherit interface OverlayComponent { exitOverlay, startOverlay }

val LocalStopwatchOverlayComponent = staticCompositionLocalOf<StopwatchOverlayComponent> {
  error("CompositionLocal LocalStopwatchOverlayComponent not present")
}

class StopwatchOverlayComponent(
  val context: Context,
  private val overlayState: OverlayStateFDSFSDF,
  private val stopwatchState: StopwatchStateGDFGDFG,
//  private val stopService: () -> Unit
) {
  val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  private val density = context.resources.displayMetrics.density
  private val timerSizePx = (TIMER_SIZE_DP * density).toInt()

  private val fullscreenOverlay: OverlayViewHolder
  val clickTargetOverlay: OverlayViewHolder

  private var isOverlayShowing = false

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
      CompositionLocalProvider(LocalStopwatchOverlayComponent provides this) {
        StopwatchClickTargetCDSCDS(overlayState, stopwatchState)
      }
    }

    return clickTargetOverlay
  }

  private fun initFullscreenOverlay(): OverlayViewHolder {
    val fullscreenOverlay = OverlayViewHolder(
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), context
    )
    fullscreenOverlay.params.alpha = 1f
    val inputManager = context.getSystemService(INPUT_SERVICE) as InputManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
      fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
//      fullscreenOverlay.params.alpha = .5f
    }
    fullscreenOverlay.view.setContent {
      CompositionLocalProvider(LocalStopwatchOverlayComponent provides this) {
        StopwatchOverlayVFDSDSC(overlayState, stopwatchState)
      }
    }
    return fullscreenOverlay
  }

  fun showOverlays() {
    if (isOverlayShowing) {
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

    overlayState.timerOffset = IntOffset.Zero
    clickTargetOverlay.params.x = 0
    clickTargetOverlay.params.y = 0

    clickTargetOverlay.view.disposeComposition()
    fullscreenOverlay.view.disposeComposition()

    isOverlayShowing = false
  }


}