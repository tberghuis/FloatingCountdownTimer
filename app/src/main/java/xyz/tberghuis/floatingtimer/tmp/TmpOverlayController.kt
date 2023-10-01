package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.graphics.PixelFormat
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.TIMER_SIZE_PX
import xyz.tberghuis.floatingtimer.logd

class TmpOverlayController(val service: TmpService) {

  var contentView: View? = null

  init {
    logd("TmpOverlayController init")
  }

  fun addComposeView() {
    logd("addComposeView")

    contentView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
      setContent {
        Box {
          Column {
            Text(text = "Hello World")
          }
        }
      }
    }

    reallyAddComposeView()
  }

  private fun reallyAddComposeView() {
    val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val params = WindowManager.LayoutParams(
      TIMER_SIZE_PX,
      TIMER_SIZE_PX,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    )
    windowManager.addView(contentView!!, params)
  }
}