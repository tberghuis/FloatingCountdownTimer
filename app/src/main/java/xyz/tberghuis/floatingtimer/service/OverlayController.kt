package xyz.tberghuis.floatingtimer.service

import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
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
          ClickTarget(
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
            service.state,
            this,
            stopwatchState.overlayState,
            stopwatchClickTarget,
            bubbleContent = {
              StopwatchBubble(Modifier, stopwatchState)
            },
            this::exitStopwatch,
            stopwatchState::resetStopwatchState
          ) {
            onClickStopwatchClickTarget(stopwatchState)
          }
        }
      }
    }
    return stopwatchClickTarget
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