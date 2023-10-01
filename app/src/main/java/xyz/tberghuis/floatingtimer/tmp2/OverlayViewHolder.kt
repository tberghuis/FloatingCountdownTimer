package xyz.tberghuis.floatingtimer.tmp2

import android.view.Gravity
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.service.overlayViewFactory

class OverlayViewHolder(val params: WindowManager.LayoutParams, service: FloatingService) {
  val view = overlayViewFactory(service)

  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
  }
}