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
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.tmp4.TmpService

class TmpTrashController(val service: TmpService) {
  var trashComposeView: ComposeView? = null
  var trashParams: WindowManager.LayoutParams? = null
  val trashState = TmpTrashState()


  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager


  fun addTrashView() {

    trashParams = WindowManager.LayoutParams(
      300,
      300,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ).apply {
//      gravity = Gravity.TOP or Gravity.LEFT
      gravity = Gravity.CENTER
    }

    trashComposeView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }

    trashComposeView!!.setContent {
      Box(
        modifier = Modifier.background(Color.Red),
      ) {

      }
    }
    windowManager.addView(trashComposeView, trashParams)
  }
}