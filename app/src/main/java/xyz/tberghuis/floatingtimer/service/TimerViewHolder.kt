package xyz.tberghuis.floatingtimer.service

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

//class TimerViewHolder(val service: FloatingService, val timerSizePx: Int) {
//  val params = WindowManager.LayoutParams(
//    timerSizePx,
//    timerSizePx,
//    0,
//    0,
//    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//    PixelFormat.TRANSLUCENT
//  )
//  val view = createComposeView(service)
//
//  init {
//    params.gravity = Gravity.TOP or Gravity.LEFT
//  }
//}

// https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service
fun createComposeView(service: FloatingService): ComposeView {
  return ComposeView(service).apply {
    setViewTreeSavedStateRegistryOwner(service)
    setViewTreeLifecycleOwner(service)
  }
}