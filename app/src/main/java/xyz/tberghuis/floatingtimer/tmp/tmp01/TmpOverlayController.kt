package xyz.tberghuis.floatingtimer.tmp.tmp01

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd

class TmpOverlayController(service: ProcessNameService) {
  private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val params = WindowManager.LayoutParams()
  val view = createComposeView(service)

  fun testOverlay() {
    initParams(0, 0)
    GlobalScope.launch(Main) {
      view.setContent {
        Text("hello world")
      }
      windowManager.addView(view, params)
    }
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

  private fun createComposeView(service: ProcessNameService): ComposeView {
    return ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }
  }
}