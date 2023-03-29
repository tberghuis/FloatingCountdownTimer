package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.os.Build
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import java.util.*
import kotlin.concurrent.timerTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchState

val LocalServiceState = compositionLocalOf<ServiceState> { error("No ServiceState provided") }

class OverlayController(val service: FloatingService) {
  private val countdownState = service.state.countdownState
  private val countdownIsVisible: Flow<Boolean?>
    get() = countdownState.overlayState.isVisible

  private val stopwatchState = service.state.stopwatchState
  private val stopwatchIsVisible: Flow<Boolean?>
    get() = stopwatchState.overlayState.isVisible

  private val density = service.resources.displayMetrics.density
  val timerSizePx = (TIMER_SIZE_DP * density).toInt()
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager


  init {
    logd("OverlayController init")
    setContentOverlayView()
    initViewControllers()
  }

  private fun createFullscreenOverlay(): OverlayViewHolder {
    val fullscreenOverlay: OverlayViewHolder = OverlayViewHolder(
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )

    // https://developer.android.com/reference/android/view/WindowManager.LayoutParams#MaximumOpacity
    fullscreenOverlay.params.alpha = 1f
    val inputManager = service.applicationContext.getSystemService(INPUT_SERVICE) as InputManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
      fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
    }

    fullscreenOverlay.view.setContent {
      CompositionLocalProvider(LocalServiceState provides service.state) {
        OverlayContent()
      }
    }

    return fullscreenOverlay
  }


  private fun createCountdownClickTarget(): OverlayViewHolder {
    val countdownClickTarget = OverlayViewHolder(
      WindowManager.LayoutParams(
        timerSizePx,
        timerSizePx,
        0, // todo place default position
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )
    countdownClickTarget.view.setContent {
      ClickTarget(
        service.state, this, countdownState.overlayState, countdownClickTarget, this::exitCountdown
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
    return countdownClickTarget
  }

  private fun createStopwatchClickTarget(): OverlayViewHolder {
    val stopwatchClickTarget = OverlayViewHolder(
      WindowManager.LayoutParams(
        timerSizePx,
        timerSizePx,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      ), service
    )
    stopwatchClickTarget.view.setContent {
      ClickTarget(
        service.state, this, stopwatchState.overlayState, stopwatchClickTarget, this::exitStopwatch
      ) {
        onClickStopwatchClickTarget(stopwatchState)
      }
    }
    return stopwatchClickTarget
  }


  private fun setContentOverlayView() {

  }


  private fun initViewControllers() {
    OverlayViewController(
      this::createFullscreenOverlay, deriveFullscreenVisibleFlow(), windowManager
    )
    OverlayViewController(
      this::createCountdownClickTarget, countdownIsVisible.filterNotNull(), windowManager
    )
    OverlayViewController(
      this::createStopwatchClickTarget, stopwatchIsVisible.filterNotNull(), windowManager
    )
  }


//  private fun watchState() {
//    with(CoroutineScope(Dispatchers.Main)) {
//      launch {
//        deriveFullscreenVisibleFlow().collect { showFullscreen ->
//          addOrRemoveView(fullscreenOverlay, showFullscreen)
//        }
//      }
//      launch {
//        addOrRemoveClickTargetView(countdownIsVisible, ::createCountdownClickTarget)
//      }
//      launch {
//        addOrRemoveClickTargetView(stopwatchIsVisible, ::createStopwatchClickTarget)
//      }
//    }
//  }

//  private suspend fun addOrRemoveView(
//    viewHolder: OverlayViewHolder, isVisible: Boolean
//  ) {
//
//    withContext(Dispatchers.Main) {
//      when (isVisible) {
//        true -> {
//          logd("addview ${viewHolder.view}")
//          windowManager.addView(viewHolder.view, viewHolder.params)
//        }
//        false -> {
//          logd("removeview ${viewHolder.view}")
//          // wrap in try catch???
//          windowManager.removeView(viewHolder.view)
//          viewHolder.view.disposeComposition()
//        }
//      }
//    }
//  }

  private fun deriveFullscreenVisibleFlow(): Flow<Boolean> {
    val f1 = service.state.countdownState.overlayState.isVisible
    val f2 = service.state.stopwatchState.overlayState.isVisible

    return f1.combine(f2) { b1, b2 ->
      logd("deriveFullscreenVisibleFlow b1 $b1 b2 $b2")
      if (b1 == null && b2 == null) {
        null
      } else {
        listOf(b1, b2).contains(true)
      }
    }.filterNotNull().distinctUntilChanged()
  }

//  private suspend fun addOrRemoveClickTargetView(
//    isVisible: Flow<Boolean?>, createViewHolder: () -> OverlayViewHolder
//  ) {
//    var viewHolder: OverlayViewHolder? = null
//    // isVisible first() should always be true
//    isVisible.filterNotNull().collect { isVisible ->
//      if (isVisible) {
//        viewHolder = createViewHolder()
//      }
//      addOrRemoveView(viewHolder!!, isVisible)
//    }
//  }

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
  when (running.value) {
    false -> {
      running.value = true
      Timer().scheduleAtFixedRate(timerTask {
        if (running.value) {
          stopwatchState.timeElapsed.value++
        } else {
          cancel()
        }
      }, 1000, 1000)
    }
    true -> {
      running.value = false
    }
  }
}