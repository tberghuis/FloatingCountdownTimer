package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd

class Tmp02OverlayController(val service: Tmp02Service) {
  private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  val params = WindowManager.LayoutParams()

  @OptIn(DelicateCoroutinesApi::class)
  fun testOverlay() {
    initParams(0, 0)
    val view = createComposeView(service)
    GlobalScope.launch(Main) {
      view.setContent {
        Text("hello world", modifier = Modifier.background(Color.Cyan))
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

  private fun createComposeView(service: Tmp02Service): ComposeView {
    return ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }
  }
}