package xyz.tberghuis.floatingtimer

import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

fun logd(s: String) {
  if (BuildConfig.DEBUG)
    Log.d("xxx", s)
}

// need a better place to keep this
val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal LocalHaloColour not present")
}