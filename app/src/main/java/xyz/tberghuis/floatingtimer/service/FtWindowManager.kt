package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class FtWindowManager(
  context: Context // context is really FloatingService
) {
  private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  // future.txt suspend withContext(Main)
  // wait till i write a test case to prove it will prevent ANR
  fun addView(view: View, params: ViewGroup.LayoutParams) {
    windowManager.addView(view, params)
  }

  // future.txt suspend withContext(Main+immediate) ???
  fun updateViewLayout(view: View, params: ViewGroup.LayoutParams) {
    windowManager.updateViewLayout(view, params)
  }

  // future.txt suspend withContext(Main)
  fun removeView(view: View) {
    windowManager.removeView(view)
  }
}