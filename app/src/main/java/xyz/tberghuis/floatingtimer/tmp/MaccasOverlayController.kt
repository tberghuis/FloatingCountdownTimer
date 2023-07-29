package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.overlayViewFactory

class MaccasOverlayController(val service: MaccasService) {

  val bubbleParams: WindowManager.LayoutParams
  val bubbleView: ComposeView

  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager


  init {
    logd("MaccasOverlayController init")
    bubbleParams = initBubbleLayoutParams(service)
    // todo refactor overlayViewFactory
    // OverlayComposeViewBuilder build returns ComposeView
    bubbleView = overlayViewFactory(service)
    setContentBubbleView(bubbleView)


    windowManager.addView(bubbleView, bubbleParams)

  }
}

///////////////////////////////////////////

private fun initBubbleLayoutParams(context: Context): WindowManager.LayoutParams {
  val density = context.resources.displayMetrics.density
  val overlaySizePx = (MC.OVERLAY_SIZE_DP * density).toInt()
  return WindowManager.LayoutParams(
    overlaySizePx,
    overlaySizePx,
    0,
    0,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )
}

private fun setContentBubbleView(bubbleView: ComposeView) {
  bubbleView.setContent {
    Box {
      Text("0:00")
    }
  }
}