package xyz.tberghuis.floatingtimer.tmp6

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpService

class TmpOverlayController(val service: TmpService) {
  init {
    logd("TmpOverlayController")
  }

  fun helloController() {
    logd("helloController")
  }


  fun addTimer() {

//    val params = WindowManager.LayoutParams(
//      300,
//      300,
//      0,
//      0,
//      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//      PixelFormat.TRANSLUCENT
//    ).apply {
//      gravity = Gravity.TOP or Gravity.LEFT
//    }

    val timerComposeView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }


  }


}