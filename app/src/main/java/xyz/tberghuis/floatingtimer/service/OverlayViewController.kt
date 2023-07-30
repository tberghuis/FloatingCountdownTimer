package xyz.tberghuis.floatingtimer.service

import android.view.WindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.logd

class OverlayViewController(
  val createOverlayViewHolder: () -> OverlayViewHolder,
  val isVisible: Flow<Boolean?>,
  val windowManager: WindowManager
) {
  // doitwrong
  init {
    // todo get scope from service
    CoroutineScope(Dispatchers.Main).launch {
      watchIsVisible()
    }
  }

  private suspend fun watchIsVisible() {
    var viewHolder: OverlayViewHolder? = null

    isVisible.collect { isVisible ->
      when (isVisible) {
        true -> {
          viewHolder = createOverlayViewHolder()
          windowManager.addView(viewHolder!!.view, viewHolder!!.params)
        }
        false, null -> {
          viewHolder?.let {
            windowManager.removeView(it.view)
            it.view.disposeComposition()
          }
        }
      }
    }
  }
}