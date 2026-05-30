package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class FtWindowManager(
  private val service: FloatingService // context is really FloatingService
) {
  private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  private var callStartForegroundOnce = false
  
  // future.txt suspend withContext(Main)
  // wait till i write a test case to prove it will prevent ANR
  fun addView(view: View, params: ViewGroup.LayoutParams) {
    windowManager.addView(view, params)
    if(!callStartForegroundOnce){
      callStartForegroundOnce = true
      // if I still get ForegroundServiceStartNotAllowedException
      // option are delay here, or override onWindowVisibilityChanged in AbstractComposeView
      // https://developer.android.com/about/versions/15/behavior-changes-15
      service.startInForeground()
    }
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