package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.roundToInt

class TmpLabelScreenVm(
  private val application: Application,
//  private val state: SavedStateHandle
) : AndroidViewModel(application) {
  fun createOverlay(activity: ComponentActivity) {
    logd("createOverlay")

    val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val overlaySize = 60.dp

    val sizePx: Int =
      (overlaySize.value * application.resources.displayMetrics.density).roundToInt()

    val params = WindowManager.LayoutParams(
      sizePx,
      sizePx,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    )

    val view = ComposeView(activity).apply {
      setContent {
        Text("hello overlay")
      }

      setViewTreeLifecycleOwner(activity)
      setViewTreeSavedStateRegistryOwner(activity)
//propagateViewTreeSavedStateRegistryOwner
    }


    windowManager.addView(view, params)


  }

}