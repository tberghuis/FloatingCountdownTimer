package xyz.tberghuis.floatingtimer.tmp.tmp03

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.composables.TimerText
import xyz.tberghuis.floatingtimer.composables.formatIntTimerDisplay
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpTimeDisplay(
  totalSeconds: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  // fixes bug timer would appear paused when screen turned back on
  // todo i can move this higher in the composition tree
  var forceUpdateKey by remember { mutableIntStateOf(0) }
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    val displayListener = object : DisplayManager.DisplayListener {
      override fun onDisplayAdded(p0: Int) {
      }

      override fun onDisplayRemoved(p0: Int) {
      }

      override fun onDisplayChanged(p0: Int) {
        logd("onDisplayChanged $p0")
        forceUpdateKey++
      }
    }
    val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    displayManager.registerDisplayListener(displayListener, Handler(Looper.getMainLooper()))
  }

  LaunchedEffect(forceUpdateKey) {
    logd("forceUpdateKey $forceUpdateKey")
  }

  val hours = totalSeconds / (3600)
  val minutes = (totalSeconds % (3600)) / 60
  val seconds = totalSeconds % 60
  var text = "${formatIntTimerDisplay(minutes)}:${formatIntTimerDisplay(seconds)}"
  if (hours > 0) {
    text = "${formatIntTimerDisplay(hours)}:$text"
  }

  key(forceUpdateKey) {
    TimerText(
      text,
      fontSize,
      isBackgroundTransparent
    )
  }
}