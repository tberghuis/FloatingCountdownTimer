package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpLabelScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {

  val params = WindowManager.LayoutParams(
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.MATCH_PARENT,
    0,
    0,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )

  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
    logd("TimerViewHolder params $params")
  }

  val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  var view: ComposeView? = null

  fun createOverlay(activity: ComponentActivity) {
    logd("createOverlay")
    view = createViewRow(activity)
    windowManager.addView(view, params)
  }
}