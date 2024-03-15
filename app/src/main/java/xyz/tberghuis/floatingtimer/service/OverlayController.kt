package xyz.tberghuis.floatingtimer.service

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.service.countdown.Countdown
import xyz.tberghuis.floatingtimer.service.countdown.CountdownView
import xyz.tberghuis.floatingtimer.service.stopwatch.Stopwatch
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchView
import kotlin.math.max
import kotlin.math.min

class OverlayController(val service: FloatingService) {
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val trashController = TrashController(windowManager, service)
  private val bubbleSet = mutableSetOf<Bubble>()

  fun getNumberOfBubbles(): Int {
    return bubbleSet.size
  }

  // doitwrong
  fun addStopwatch(haloColor: Color, timerShape: String) {
    service.scope.launch {
      val bubbleScale = withContext(IO) {
        service.application.providePreferencesRepository().bubbleScaleFlow.first()
      }
      val stopwatch = Stopwatch(service, bubbleScale, haloColor, timerShape)
      val stopwatchView = @Composable { StopwatchView(stopwatch) }
      // does coroutine dispatcher matter here???
      withContext(Main) {
        addBubble(stopwatch, stopwatchView)
      }
    }
  }

  fun addCountdown(durationSeconds: Int, haloColor: Color, timerShape: String) {
    service.scope.launch {
      val bubbleScale = withContext(IO) {
        service.application.providePreferencesRepository().bubbleScaleFlow.first()
      }
      val countdown = Countdown(service, durationSeconds, bubbleScale, haloColor, timerShape)
      val countdownView = @Composable { CountdownView(countdown) }
      withContext(Main) {
        addBubble(countdown, countdownView)
      }
    }
  }

  private fun addBubble(bubble: Bubble, bubbleView: @Composable () -> Unit) {
    logd("OverlayController addBubble")
    bubbleSet.add(bubble)

    bubble.viewHolder.view.setContent {
      bubbleView()
    }

    clickTargetSetOnTouchListener(
      bubble = bubble,
      exitTimer = {
        bubble.exit()
        bubbleSet.remove(bubble)
        if (bubbleSet.isEmpty()) {
          service.stopSelf()
        }
      },
      onDoubleTap = { bubble.reset() },
      onTap = { bubble.onTap() }
    )
    windowManager.addView(bubble.viewHolder.view, bubble.viewHolder.params)
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun clickTargetSetOnTouchListener(
    bubble: Bubble,
    exitTimer: () -> Unit,
    onDoubleTap: () -> Unit,
    onTap: () -> Unit,
  ) {

    logd("clickTargetSetOnTouchListener bubble $bubble")

    var paramStartDragX: Int = 0
    var paramStartDragY: Int = 0
    var startDragRawX: Float = 0F
    var startDragRawY: Float = 0F

    val tapDetector = GestureDetector(service, object : GestureDetector.SimpleOnGestureListener() {
      override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        logd("onSingleTapConfirmed")
        onTap()
        return true
      }

      override fun onDoubleTap(e: MotionEvent): Boolean {
        logd("onDoubleTap")
        onDoubleTap()
        return true
      }
    })

    bubble.viewHolder.view.setOnTouchListener { _, event ->

      logd("bubble.viewHolder.view.setOnTouchListener")

      if (tapDetector.onTouchEvent(event)) {
        // just to be safe
        trashController.isBubbleDragging.value = false
        return@setOnTouchListener true
      }

      val params = bubble.viewHolder.params
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          logd("setOnTouchListener ACTION_DOWN")
          paramStartDragX = params.x
          paramStartDragY = params.y
          startDragRawX = event.rawX
          startDragRawY = event.rawY
        }

        MotionEvent.ACTION_MOVE -> {
          trashController.currentDraggingBubble.value = bubble
          trashController.isBubbleDragging.value = true
          params.x = (paramStartDragX + (event.rawX - startDragRawX)).toInt()
          params.y = (paramStartDragY + (event.rawY - startDragRawY)).toInt()
          updateClickTargetParamsWithinScreenBounds(bubble.viewHolder)
        }

        MotionEvent.ACTION_UP -> {
          trashController.isBubbleDragging.value = false
          trashController.currentDraggingBubble.value = null
          // refactor how trash works???
          if (trashController.isBubbleHoveringTrash) {
            trashController.isBubbleHoveringTrash = false
            exitTimer()
          }
        }
      }
      false
    }
  }

  private fun updateClickTargetParamsWithinScreenBounds(
    viewHolder: TimerViewHolder,
  ) {
    val params = viewHolder.params
    var x = params.x
    var y = params.y
    x = max(x, 0)
    x = min(x, ScreenEz.safeWidth - viewHolder.widthPx)
    y = max(y, 0)
    y = min(y, ScreenEz.safeHeight - viewHolder.heightPx)
    params.x = x
    params.y = y
    try {
      windowManager.updateViewLayout(viewHolder.view, params)
    } catch (e: IllegalArgumentException) {
      // this was happening in prod, can't reproduce
      Log.e("OverlayController", "IllegalArgumentException: $e")
    }
    trashController.bubbleDraggingPosition.value = IntOffset(params.x, params.y)
  }

  fun onConfigurationChanged() {
    bubbleSet.forEach {
      updateClickTargetParamsWithinScreenBounds(it.viewHolder)
    }
  }

  fun exitAll() {
    bubbleSet.forEach { bubble ->
      bubble.exit()
    }
    service.stopSelf()
  }

  fun resetAll() {
    bubbleSet.forEach { bubble ->
      bubble.reset()
    }
  }
}