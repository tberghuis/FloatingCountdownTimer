package xyz.tberghuis.floatingtimer.tmp2

import android.util.Log
import android.view.WindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OverlayViewController(
  val createOverlayViewHolder: () -> OverlayViewHolder,
  val isVisible: Flow<Boolean?>,
  val windowManager: WindowManager,
  scope: CoroutineScope
) {
  // doitwrong
  init {
    scope.launch(Dispatchers.Main) {
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
          viewHolder?.view?.let {
            try {
              windowManager.removeView(it)
              it.disposeComposition()
            } catch (e: IllegalArgumentException) {
              Log.e("OverlayViewController", "IllegalArgumentException $e")
            }
          }
        }
      }
    }
  }
}