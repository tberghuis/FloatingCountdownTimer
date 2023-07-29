package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.ComposeView
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.overlayViewFactory

val LocalMaccasOverlayController = compositionLocalOf<MaccasOverlayController> {
  error("CompositionLocal LocalMaccasOverlayController not present")
}


class MaccasOverlayController(val service: MaccasService) {

  val bubbleParams: WindowManager.LayoutParams
  val bubbleView: ComposeView

  val bubbleState = MaccasBubbleState()

  val fullscreenParams: WindowManager.LayoutParams
  val fullscreenView: ComposeView


  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager


  init {
    logd("MaccasOverlayController init")
    bubbleParams = initBubbleLayoutParams(service)
    // todo refactor overlayViewFactory
    // OverlayComposeViewBuilder build returns ComposeView
    bubbleView = overlayViewFactory(service)
    setContentBubbleView()

    fullscreenParams = initFullscreenLayoutParams()
    fullscreenView = overlayViewFactory(service)
    setContentFullscreenView()

    windowManager.addView(bubbleView, bubbleParams)
    windowManager.addView(fullscreenView, fullscreenParams)

  }

  private fun setContentBubbleView() {
    bubbleView.setContent {
      CompositionLocalProvider(LocalMaccasOverlayController provides this) {
        MaccasBubble()
      }
    }
  }

  private fun setContentFullscreenView() {
    fullscreenView.setContent {
      CompositionLocalProvider(LocalMaccasOverlayController provides this) {
        MaccasFullscreen()
      }
    }
  }
}

///////////////////////////////////////////

private fun initBubbleLayoutParams(context: Context): WindowManager.LayoutParams {
  val density = context.resources.displayMetrics.density
  val overlaySizePx = (MC.OVERLAY_SIZE_DP * density).toInt()
  val params = WindowManager.LayoutParams(
    overlaySizePx,
    overlaySizePx,
    0,
    0,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )
  params.gravity = Gravity.TOP or Gravity.LEFT
  return params
}


private fun initFullscreenLayoutParams(): WindowManager.LayoutParams {
  val params = WindowManager.LayoutParams(
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )
  params.gravity = Gravity.TOP or Gravity.LEFT
  return params
}







