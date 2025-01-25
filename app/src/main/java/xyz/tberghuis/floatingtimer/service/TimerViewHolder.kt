package xyz.tberghuis.floatingtimer.service

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpTimerViewHolder

class TimerViewHolder(
  override val service: FloatingService,
  widthPx: Int,
  heightPx: Int,
  x: Int? = null,
  y: Int? = null
) : TmpTimerViewHolder(
  service,
  x,
  y
) {

}

class XxxTimerViewHolder(
  val service: FloatingService,
  widthPx: Int,
  heightPx: Int,
  x: Int? = null,
  y: Int? = null
) {
  val params = WindowManager.LayoutParams(
    widthPx,
    heightPx,
    x ?: 0,
    y ?: 0,
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

// https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service
fun createComposeView(service: FloatingService): ComposeView {
  return ComposeView(service).apply {
    setViewTreeSavedStateRegistryOwner(service)
    setViewTreeLifecycleOwner(service)
  }
}