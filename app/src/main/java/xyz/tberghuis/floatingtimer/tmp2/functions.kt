package xyz.tberghuis.floatingtimer.tmp2

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.compositionLocalOf

fun Context.getWindowManager(): WindowManager {
  return getSystemService(Context.WINDOW_SERVICE) as WindowManager
}



val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}

