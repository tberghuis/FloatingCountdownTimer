package xyz.tberghuis.floatingtimer.tmp5

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.createComposeView

class TimerViewHolder(val service: FloatingService, val widthPx: Int, val heightPx: Int) {
  val params = WindowManager.LayoutParams(
    widthPx,
    heightPx,
    0,
    0,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )
  val view = createComposeView(service)

  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
  }
}