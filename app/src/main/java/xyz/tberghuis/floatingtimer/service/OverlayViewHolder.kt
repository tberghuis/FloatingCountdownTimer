package xyz.tberghuis.floatingtimer.service

import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class OverlayViewHolder(val params: WindowManager.LayoutParams, val service: FloatingService) {
  val view = createComposeView()
  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
  }

  // https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service
  private fun createComposeView(): ComposeView {
    return ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)
    }
  }
}