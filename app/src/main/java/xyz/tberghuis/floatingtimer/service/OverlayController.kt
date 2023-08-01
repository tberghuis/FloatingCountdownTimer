package xyz.tberghuis.floatingtimer.service

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import xyz.tberghuis.floatingtimer.LocalHaloColour
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.TIMER_SIZE_PX
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.CountdownBubble
import xyz.tberghuis.floatingtimer.service.countdown.CountdownOverlay
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchBubble
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchOverlay
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState
import kotlin.math.max
import kotlin.math.min

val LocalServiceState = compositionLocalOf<ServiceState> { error("No ServiceState provided") }

class OverlayController(val service: FloatingService) {
  private val countdownState = service.state.countdownState
  private val countdownIsVisible: Flow<Boolean?>
    get() = countdownState.overlayState.isVisible

  private val stopwatchState = service.state.stopwatchState
  private val stopwatchIsVisible: Flow<Boolean?>
    get() = stopwatchState.overlayState.isVisible

  private val density = service.resources.displayMetrics.density

  //  val timerSizePx = (TIMER_SIZE_DP * density).toInt()
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  init {
    logd("OverlayController init")
    initViewControllers()
  }

  private fun createFullscreenOverlay(overlayContent: @Composable () -> Unit): OverlayViewHolder {
    val fullscreenOverlay = OverlayViewHolder(
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )

    fullscreenOverlay.view.setContent {
      val haloColour =
        providePreferencesRepository(service.application).haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
      CompositionLocalProvider(LocalServiceState provides service.state) {
        CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
          overlayContent()
        }
      }
    }

    return fullscreenOverlay
  }

  private fun createCountdownClickTarget(): OverlayViewHolder {
    val countdownClickTarget = OverlayViewHolder(
      WindowManager.LayoutParams(
        TIMER_SIZE_PX,
        TIMER_SIZE_PX,
        0, // todo place default position
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )
    countdownClickTarget.view.setContent {
      val haloColour =
        providePreferencesRepository(service.application).haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
      CompositionLocalProvider(LocalServiceState provides service.state) {
        CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
          TmpClickTarget(
            service.state, this, countdownState.overlayState, countdownClickTarget,
            bubbleContent = {
              CountdownBubble(Modifier, countdownState)
            },
            this::exitCountdown, countdownState::resetTimerState
          ) {
            logd("click target onclick")
            when (countdownState.timerState.value) {
              is TimerStatePaused -> {
                countdownState.timerState.value = TimerStateRunning
              }

              is TimerStateRunning -> {
                countdownState.timerState.value = TimerStatePaused
              }

              is TimerStateFinished -> {
                countdownState.resetTimerState(countdownState.durationSeconds)
              }
            }
          }
        }
      }
    }
    return countdownClickTarget
  }

  private fun createStopwatchClickTarget(): OverlayViewHolder {
    val stopwatchClickTarget = OverlayViewHolder(
      WindowManager.LayoutParams(
        TIMER_SIZE_PX,
        TIMER_SIZE_PX,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )
    stopwatchClickTarget.view.setContent {
      val haloColour =
        providePreferencesRepository(service.application).haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
      CompositionLocalProvider(LocalServiceState provides service.state) {
        CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
          ClickTarget(
            bubbleContent = {
              StopwatchBubble(Modifier, stopwatchState)
            },
            stopwatchState::resetStopwatchState
          ) {
            onClickStopwatchClickTarget(stopwatchState)
          }
        }
      }
    }

    clickTargetSetupDragging(stopwatchClickTarget, service.state.stopwatchState.overlayState)
    return stopwatchClickTarget
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun clickTargetSetupDragging(viewHolder: OverlayViewHolder, overlayState: OverlayState) {

    var paramStartDragX: Int = 0
    var paramStartDragY: Int = 0
    var startDragRawX: Float = 0F
    var startDragRawY: Float = 0F

    viewHolder.view.setOnTouchListener { v, event ->
      val params = viewHolder.params
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          paramStartDragX = params.x
          paramStartDragY = params.y
          startDragRawX = event.rawX
          startDragRawY = event.rawY
        }

        MotionEvent.ACTION_MOVE -> {
          overlayState.isDragging.value = true
          params.x = (paramStartDragX + (event.rawX - startDragRawX)).toInt()
          params.y = (paramStartDragY + (event.rawY - startDragRawY)).toInt()
          updateClickTargetParamsWithinScreenBounds(viewHolder, overlayState)
        }

        MotionEvent.ACTION_UP -> {
          overlayState.isDragging.value = false
        }
      }
      false
    }
  }

  fun updateClickTargetParamsWithinScreenBounds(
    viewHolder: OverlayViewHolder,
    overlayState: OverlayState
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
    windowManager.updateViewLayout(viewHolder.view, params)
    overlayState.timerOffset = IntOffset(params.x, params.y)
  }

  private fun initViewControllers() {
    OverlayViewController(
      this::createCountdownClickTarget,
      countdownIsVisible.filterNotNull(),
      windowManager,
      service.scope
    )
    OverlayViewController(
      {
        createFullscreenOverlay {
          CountdownOverlay(service.state)
        }
      },
      service.state.countdownState.overlayState.isDragging,
      windowManager, service.scope
    )

    OverlayViewController(
      this::createStopwatchClickTarget,
      stopwatchIsVisible.filterNotNull(),
      windowManager,
      service.scope
    )
    OverlayViewController(
      {
        createFullscreenOverlay {
          StopwatchOverlay()
        }
      },
      service.state.stopwatchState.overlayState.isDragging,
      windowManager, service.scope
    )
  }

  fun exitCountdown() {
    countdownState.overlayState.isVisible.value = false
    countdownState.overlayState.reset()
    countdownState.resetTimerState()
    exitIfNoTimers()
  }

  private fun exitIfNoTimers() {
    val countdownIsVisible = countdownState.overlayState.isVisible.value
    val stopwatchIsVisible = stopwatchState.overlayState.isVisible.value
    if (countdownIsVisible != true && stopwatchIsVisible != true) {
      service.stopSelf()
    }
  }

  fun exitStopwatch() {
    val stopwatchState = service.state.stopwatchState
    stopwatchState.overlayState.isVisible.value = false
    stopwatchState.overlayState.reset()
    stopwatchState.resetStopwatchState()
    exitIfNoTimers()
  }
}

fun onClickStopwatchClickTarget(stopwatchState: StopwatchState) {
  logd("click target start pause")
  val running = stopwatchState.isRunningStateFlow
  running.value = !running.value
}