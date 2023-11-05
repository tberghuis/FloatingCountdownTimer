package xyz.tberghuis.floatingtimer.tmp2

import android.content.Context
import android.view.WindowManager

fun Context.getWindowManager(): WindowManager {
  return getSystemService(Context.WINDOW_SERVICE) as WindowManager
}
