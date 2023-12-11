package xyz.tberghuis.floatingtimer.service

import android.graphics.PixelFormat
import android.util.Log
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.composables.LocalFloatingService

// todo move windowManager to service
class TrashController(
  val windowManager: WindowManager,
  private val service: FloatingService,
) {
  val isBubbleDragging = MutableStateFlow(false)

  // todo refactor, use currentDraggingBubble
  //   put viewHolder params.x params.y in MutableStateFlow
  //   collect for windowManager.update
  val bubbleDraggingPosition = mutableStateOf(IntOffset.Zero)
  val currentDraggingBubble = mutableStateOf<Bubble?>(null)

  // todo make generic
  var isBubbleHoveringTrash = false

  private var overlay: ComposeView? = null
  private val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    PixelFormat.TRANSLUCENT
  )

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
          windowManager.addView(newOverlay, params)
        }

        false -> {
          overlay?.let {
            try {
              windowManager.removeView(it)
              // do i need disposecomposition???
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
        TrashOverlay()
      }
    }
    return view
  }
}