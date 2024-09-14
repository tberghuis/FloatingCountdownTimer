package xyz.tberghuis.floatingtimer.tmp6

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpService

class TmpOverlayController(val service: TmpService) {
  var timerComposeView: ComposeView? = null
  var timerParams: WindowManager.LayoutParams? = null

  init {
    logd("TmpOverlayController")
  }

  fun helloController() {
    logd("helloController")
  }


  fun addTimer() {

    timerParams = WindowManager.LayoutParams(
      300,
      300,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ).apply {
      gravity = Gravity.TOP or Gravity.LEFT
    }

    timerComposeView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }

    timerComposeView!!.setContent {
      Box(
        modifier = Modifier.background(Color.Blue),
      ) {

      }
    }


    val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.addView(timerComposeView, timerParams)

  }


}