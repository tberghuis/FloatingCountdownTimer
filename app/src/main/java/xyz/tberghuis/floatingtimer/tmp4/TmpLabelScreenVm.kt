package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.roundToInt

class TmpLabelScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  val sizePx: Int = 60.dpToPx(application)
  val params = WindowManager.LayoutParams(
    sizePx,
    sizePx,
    0,
    0,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )
  val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  var view: ComposeView? = null

  fun createOverlay(activity: ComponentActivity) {
    logd("createOverlay")
    view = createView(activity)
    windowManager.addView(view, params)
  }


  fun updateSize() {
    params.x = 120.dpToPx(application)
    params.y = 120.dpToPx(application)
    windowManager.updateViewLayout(view, params)
  }
}

fun Int.dpToPx(context: Context): Int {
  return (this * context.resources.displayMetrics.density).roundToInt()
}

fun createView(activity: ComponentActivity): ComposeView {
  return ComposeView(activity).apply {
    setContent {
      Box(
        modifier = Modifier
          .size(120.dp)
          .background(Color.Red),
        contentAlignment = Alignment.TopStart,
      ) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .background(Color.Green),
          contentAlignment = Alignment.TopStart,
        ) {
          Text("hello overlay")
        }
      }
    }
    setViewTreeLifecycleOwner(activity)
    setViewTreeSavedStateRegistryOwner(activity)
  }
}