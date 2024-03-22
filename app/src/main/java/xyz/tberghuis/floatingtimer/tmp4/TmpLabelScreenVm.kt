package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.delay
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.roundToInt

class TmpLabelScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {

//  val params = WindowManager.LayoutParams(
//    sizePx,
//    sizePx,
//    0,
//    0,
//    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//    PixelFormat.TRANSLUCENT
//  )

  val params = WindowManager.LayoutParams(
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.MATCH_PARENT,
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
    view = createView(activity, ::updateSize)

//    params.gravity = Gravity.START or Gravity.TOP


    windowManager.addView(view, params)
  }


  fun updateSize() {
//    params.x = 120.dpToPx(application)
//    params.y = 120.dpToPx(application)

    params.width = 60.dpToPx(application)
    params.height = 60.dpToPx(application)


    windowManager.updateViewLayout(view, params)
  }
}

fun Int.dpToPx(context: Context): Int {
  return (this * context.resources.displayMetrics.density).roundToInt()
}

fun createView(activity: ComponentActivity, updateSize: () -> Unit): ComposeView {
  return ComposeView(activity).apply {
    setContent {

      LaunchedEffect(Unit) {
        delay(5000)
        updateSize()
      }



      Row(
        modifier = Modifier
          .onGloballyPositioned {
            logd("row layout coords ${it.size}")
          }
          .graphicsLayer {
            this.alpha = .5f
          },
      ) {
        Row(
          // todo runOnceOnGloballyPositioned
          modifier = Modifier.onGloballyPositioned {
            logd("inner row layout coords ${it.size}")
          },
        ) {
          Box(
            modifier = Modifier
              .size(60.dp)
              .background(Color.Red),
            contentAlignment = Alignment.TopStart,
          ) {
            Text("red")
          }
          Box(
            modifier = Modifier
              .size(60.dp)
              .background(Color.Green),
            contentAlignment = Alignment.TopStart,
          ) {
            Text("green")
          }
        }
      }
    }
    setViewTreeLifecycleOwner(activity)
    setViewTreeSavedStateRegistryOwner(activity)
  }
}