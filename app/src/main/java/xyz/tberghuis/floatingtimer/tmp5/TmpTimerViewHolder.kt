package xyz.tberghuis.floatingtimer.tmp5

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.createComposeView

class TmpTimerViewHolder(val service: FloatingService, var widthPx: Int, var heightPx: Int) {
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
    logd("TimerViewHolder params $params")
  }
}
