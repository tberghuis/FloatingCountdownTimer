package xyz.tberghuis.floatingtimer

import android.view.Gravity
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.service.XFloatingService
import xyz.tberghuis.floatingtimer.service.overlayViewFactory

class OverlayViewHolder(val params: WindowManager.LayoutParams, service: XFloatingService) {
  val view = overlayViewFactory(service)

  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
  }
}