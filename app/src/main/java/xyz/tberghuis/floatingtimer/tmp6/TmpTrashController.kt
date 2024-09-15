package xyz.tberghuis.floatingtimer.tmp6

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpService


class TmpTrashController(val service: TmpService) {
  var trashComposeView: ComposeView? = null
  var trashParams: WindowManager.LayoutParams? = null
  val trashState = TmpTrashState()


  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  fun printBounds() {
    if (Build.VERSION.SDK_INT >= 30) {
      val bounds = windowManager.currentWindowMetrics.bounds
      logd("addTrashView bounds $bounds")
    }
  }

  fun addTrashView() {
    trashParams = WindowManager.LayoutParams(
      300,
      300,
      200,
      -200,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ).apply {
      gravity = Gravity.TOP or Gravity.LEFT
//      gravity = Gravity.CENTER
    }

    trashComposeView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }

    trashComposeView!!.setContent {
      Box(
        modifier = Modifier
          .background(Color.Red)
          .onGloballyPositioned {
            logd("trashComposeView onGloballyPositioned LayoutCoordinates $it")
            val boundsInRoot = it.boundsInRoot()
            val boundsInParent = it.boundsInParent()
            val boundsInWindow = it.boundsInWindow()
            logd("boundsInRoot $boundsInRoot")
            logd("boundsInParent $boundsInParent")
            logd("boundsInWindow $boundsInWindow")
          },
      ) {

      }
    }
    windowManager.addView(trashComposeView, trashParams)
  }

  fun getTrashPosition() {
    val screen = IntArray(2)
    trashComposeView!!.getLocationOnScreen(screen)
    logd("getTrashPosition screen ${screen[0]} ${screen[1]}")
  }
}