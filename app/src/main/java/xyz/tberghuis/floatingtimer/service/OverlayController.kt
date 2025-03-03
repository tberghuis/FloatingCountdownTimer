package xyz.tberghuis.floatingtimer.service

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.composables.LocalTimerViewHolder
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.Countdown
import xyz.tberghuis.floatingtimer.service.countdown.CountdownView
import xyz.tberghuis.floatingtimer.service.stopwatch.Stopwatch
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchView
import kotlin.math.max
import kotlin.math.min

class OverlayController(val service: FloatingService) {
  val trashController = TrashController(service)
  private val bubbleSet = mutableSetOf<Bubble>()

  fun getNumberOfBubbles(): Int {
    return bubbleSet.size
  }

  fun addStopwatch(
    haloColor: Color,
    timerShape: String,
    label: String?,
    isBackgroundTransparent: Boolean,
    start: Boolean,
    savedTimer: SavedTimer? = null,
  ) {
    service.scope.launch {
      val bubbleScale = withContext(IO) {
        service.application.preferencesRepository.bubbleScaleFlow.first()
      }
      val stopwatch =
        Stopwatch(
          service,
          bubbleScale,
          haloColor,
          timerShape,
          label,
          isBackgroundTransparent,
          savedTimer,
          start
        )
      val stopwatchView = @Composable {
        CompositionLocalProvider(LocalTimerViewHolder provides stopwatch.viewHolder) {
          StopwatchView(stopwatch)
        }
      }
      // will crash if not Main dispatcher
      withContext(Main) {
        addBubble(stopwatch, stopwatchView)
      }
    }
  }

  fun addCountdown(
    durationSeconds: Int,
    haloColor: Color,
    timerShape: String,
    label: String?,
    isBackgroundTransparent: Boolean,
    savedTimer: SavedTimer? = null,
    start: Boolean = false
  ) {
    service.scope.launch {
      val bubbleScale = withContext(IO) {
        service.application.preferencesRepository.bubbleScaleFlow.first()
      }
      val countdown = Countdown(
        service,
        durationSeconds,
        bubbleScale,
        haloColor,
        timerShape,
        label,
        isBackgroundTransparent,
        savedTimer,
        start
      )
      val countdownView = @Composable {
        CompositionLocalProvider(LocalTimerViewHolder provides countdown.viewHolder) {
          CountdownView(countdown)
        }
      }
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
    logd("addBubble addView params ${bubble.viewHolder.params.x} ${bubble.viewHolder.params.y}")
    service.ftWindowManager.addView(bubble.viewHolder.view, bubble.viewHolder.params)
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

      logd("bubble.viewHolder.view.setOnTouchListener event $event")

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

          trashController.overlay?.let {
            trashController.isBubbleHoveringTrash =
              calcIsBubbleHoverTrash(bubble.viewHolder.view, it)
          }
        }

        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
          trashController.isBubbleDragging.value = false
          trashController.currentDraggingBubble.value = null
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
    val view = viewHolder.view
    var x = params.x
    var y = params.y
    x = max(x, 0)
    x = min(x, ScreenEz.safeWidth - view.width)
    y = max(y, 0)
    y = min(y, ScreenEz.safeHeight - view.height)
    params.x = x
    params.y = y
    try {
      service.ftWindowManager.updateViewLayout(viewHolder.view, params)
    } catch (e: IllegalArgumentException) {
      // this was happening in prod, can't reproduce
      Log.e("OverlayController", "IllegalArgumentException: $e")
    }
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

  fun saveTimerPositions() {
    logd("saveTimerPositions")
    bubbleSet.forEach { bubble ->
      bubble.saveTimerPosition()
    }
  }
}

fun calcIsBubbleHoverTrash(
  timerView: ComposeView,
  trashView: ComposeView,
): Boolean {
  val timerWidthPx = timerView.width
  val timerHeightPx = timerView.height
  val trashWidthPx = trashView.width
  val trashHeightPx = trashView.height

  val timerLocation = IntArray(2)
  timerView.getLocationOnScreen(timerLocation)

  val trashLocation = IntArray(2)
  trashView.getLocationOnScreen(trashLocation)

  val timerCenterX = timerLocation[0] + (timerWidthPx / 2f)
  val timerCenterY = timerLocation[1] + (timerHeightPx / 2f)

  val trashLeft = trashLocation[0]
  val trashRight = trashLocation[0] + trashWidthPx
  val trashTop = trashLocation[1]
  val trashBottom = trashLocation[1] + trashHeightPx

  logd("timer center $timerCenterX $timerCenterY")
  logd("trash rect $trashLeft $trashRight $trashTop $trashBottom")

  return !(timerCenterX < trashLeft ||
      timerCenterX > trashRight ||
      timerCenterY < trashTop ||
      timerCenterY > trashBottom)
}
