package xyz.tberghuis.floatingtimer.service

import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.TRASH_SIZE_DP
import xyz.tberghuis.floatingtimer.composables.LocalFloatingService
import xyz.tberghuis.floatingtimer.composables.Trash
import xyz.tberghuis.floatingtimer.tmp4.TmpBubble as Bubble

class TrashController(
//  private val windowManager: WindowManager,
  private val service: FloatingService,
) {
  val isBubbleDragging = MutableStateFlow(false)
  val currentDraggingBubble = mutableStateOf<Bubble?>(null)
  var isBubbleHoveringTrash by mutableStateOf(false)
  var overlay: ComposeView? = null

  private val trashSizePx =
    dimensionDpToPx(TRASH_SIZE_DP.dp, service.resources.displayMetrics.density)

  private val params = WindowManager.LayoutParams(
    trashSizePx,
    trashSizePx,
    0,
    dimensionDpToPx(30.dp, service.resources.displayMetrics.density), // vert padding from nav bar
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  ).apply {
    gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
  }

  init {
    service.scope.launch(Dispatchers.Main) {
      addRemoveTrashOverlay()
    }
  }

  private suspend fun addRemoveTrashOverlay() {
    isBubbleDragging.collect { isVisible ->
      when (isVisible) {
        true -> {
          val newOverlay = createTrashView()
          overlay = newOverlay
          service.ftWindowManager.addView(newOverlay, params)
        }

        false -> {
          overlay?.let {
            try {
              service.ftWindowManager.removeView(it)
            } catch (e: IllegalArgumentException) {
              Log.e("OverlayViewController", "IllegalArgumentException $e")
            } finally {
              overlay = null
            }
          }
        }
      }
    }
  }

  private fun createTrashView(): ComposeView {
    val view = createComposeView(service)
    view.setContent {
      CompositionLocalProvider(LocalFloatingService provides service) {
        Trash()
      }
    }
    return view
  }
}