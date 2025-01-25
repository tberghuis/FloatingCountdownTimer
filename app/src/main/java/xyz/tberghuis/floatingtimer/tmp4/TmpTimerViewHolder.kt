package xyz.tberghuis.floatingtimer.tmp4

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.createComposeView

open class TmpTimerViewHolder(
  open val service: FloatingService,
  x: Int? = null,
  y: Int? = null
) {
  val params = WindowManager.LayoutParams()
  val view = createComposeView(service)

  init {
    initParams(x, y)
    logd("TimerViewHolder params $params")
  }

  private fun initParams(
    x: Int?,
    y: Int?
  ) {
    params.x = x ?: 0
    params.y = y ?: 0
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    params.flags =
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    params.format = PixelFormat.TRANSLUCENT
    params.gravity = Gravity.TOP or Gravity.LEFT
    logd("params x y ${params.x} ${params.y}")
  }
}