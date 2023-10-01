package xyz.tberghuis.floatingtimer.tmp

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd

class TmpOverlayController(val service: TmpService) {

  var contentView: View? = null

  init {
    logd("TmpOverlayController init")
  }

  fun addComposeView() {
    logd("addComposeView")

    contentView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
      setContent {
        Box {
          Column {
            Text(text = "Hello World")
          }
        }
      }
    }
  }
}