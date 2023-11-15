package xyz.tberghuis.floatingtimer.tmp2

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.IntOffset
import com.torrydo.screenez.ScreenEz
import xyz.tberghuis.floatingtimer.LocalHaloColour
import xyz.tberghuis.floatingtimer.TIMER_SIZE_PX
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import kotlin.math.max
import kotlin.math.min

class OverlayController(val service: FloatingService) {
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  val trashController = TrashController(windowManager, service)

  // todo bubbleSet
  val bubbleSet = mutableSetOf<Bubble>()

  fun addStopwatch() {
    logd("OverlayController addStopwatch")
    val stopwatch = Stopwatch(service)
    val stopwatchView = @Composable { StopwatchView(stopwatch) }
    addBubble(stopwatch, stopwatchView)
  }

  fun addCountdown() {
    logd("OverlayController addStopwatch")
    val countdown = Countdown(service)
    val countdownView = @Composable { CountdownView(countdown) }
    addBubble(countdown, countdownView)
  }

  private fun addBubble(bubble: Bubble, bubbleView: @Composable () -> Unit) {
    logd("OverlayController addBubble")
    bubbleSet.add(bubble)

    bubble.viewHolder.view.setContent {
      val haloColour =
        service.application.providePreferencesRepository().haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
      CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
        // todo provide bubble
        bubbleView()
      }
      LaunchedEffect(Unit) {
        // todo configurationChanged collect
        // updateClickTargetParamsWithinScreenBounds
      }
    }

    clickTargetSetOnTouchListener(
      viewHolder = bubble.viewHolder,
      exitTimer = {
        bubble.exit()
        bubbleSet.remove(bubble)
      },
      onDoubleTap = { bubble.reset() },
      onTap = { bubble.onTap() }
    )
    windowManager.addView(bubble.viewHolder.view, bubble.viewHolder.params)
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun clickTargetSetOnTouchListener(
    viewHolder: TimerViewHolder,
    exitTimer: () -> Unit,
    onDoubleTap: () -> Unit,
    onTap: () -> Unit
  ) {

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

    viewHolder.view.setOnTouchListener { _, event ->
      if (tapDetector.onTouchEvent(event)) {
        // just to be safe
        trashController.isBubbleDragging.value = false
        return@setOnTouchListener true
      }

      val params = viewHolder.params
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {

          logd("setOnTouchListener ACTION_DOWN")

          paramStartDragX = params.x
          paramStartDragY = params.y
          startDragRawX = event.rawX
          startDragRawY = event.rawY
        }

        MotionEvent.ACTION_MOVE -> {
          trashController.isBubbleDragging.value = true
          params.x = (paramStartDragX + (event.rawX - startDragRawX)).toInt()
          params.y = (paramStartDragY + (event.rawY - startDragRawY)).toInt()
          updateClickTargetParamsWithinScreenBounds(viewHolder)
        }

        MotionEvent.ACTION_UP -> {
          trashController.isBubbleDragging.value = false
          // todo
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
    x = min(x, ScreenEz.safeWidth - TIMER_SIZE_PX)
    y = max(y, 0)
    y = min(y, ScreenEz.safeHeight - TIMER_SIZE_PX)
    params.x = x
    params.y = y
    try {
      windowManager.updateViewLayout(viewHolder.view, params)
    } catch (e: IllegalArgumentException) {
      // this was happening in prod, can't reproduce
      Log.e("OverlayController", "IllegalArgumentException: $e")
    }
//    overlayState.timerOffset = IntOffset(params.x, params.y)
    trashController.bubbleDraggingPosition.value = IntOffset(params.x, params.y)
  }

  fun exitAll() {
    bubbleSet.forEach { bubble ->
      bubble.exit()
    }
//    bubbleSet.clear()


//    logd("exitAll bubbleSet size1 ${bubbleSet.size}")
//
//    val i = bubbleSet.iterator()
//    while (i.hasNext()) {
//      val bubble = i.next()
//      bubble.exit()
//    }
//
//    logd("exitAll bubbleSet size2 ${bubbleSet.size}")

    service.stopSelf()
  }
}