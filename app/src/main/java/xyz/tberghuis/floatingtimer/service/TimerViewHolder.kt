package xyz.tberghuis.floatingtimer.service

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd

class TimerViewHolder(
  val service: FloatingService,
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

// https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service
fun createComposeView(service: FloatingService): ComposeView {
  return ComposeView(service).apply {
    setViewTreeSavedStateRegistryOwner(service)
    setViewTreeLifecycleOwner(service)
  }
}