package xyz.tberghuis.floatingtimer.tmp4

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class FtWindowManager(
  context: Context
) {
  private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  fun addView(view: View, params: ViewGroup.LayoutParams) {
    windowManager.addView(view, params)
  }
  fun updateViewLayout(view: View, params: ViewGroup.LayoutParams) {
    windowManager.updateViewLayout(view, params)
  }
  fun removeView(view: View) {
    windowManager.removeView(view)
  }
}