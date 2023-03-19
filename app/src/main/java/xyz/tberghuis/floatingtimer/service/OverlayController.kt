package xyz.tberghuis.floatingtimer.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.OverlayViewHolder
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver
import xyz.tberghuis.floatingtimer.service.countdown.CountdownState

//val LocalOverlayController =
//  compositionLocalOf<OverlayController> { error("No OverlayController provided") }

val LocalServiceState =
  compositionLocalOf<ServiceState> { error("No ServiceState provided") }


class OverlayController(val service: FloatingService) {


  private var pendingAlarm: PendingIntent? = null

  private val countdownState = service.state.countdownState
  private val countdownIsVisible: Flow<Boolean?>
    get() = countdownState.overlayState.isVisible


  private val density = service.resources.displayMetrics.density
  val timerSizePx = (TIMER_SIZE_DP * density).toInt()
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  private val countdownClickTarget = OverlayViewHolder(
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

  val fullscreenOverlay: OverlayViewHolder = OverlayViewHolder(
    WindowManager.LayoutParams(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ), service
  )

  init {
    logd("OverlayController init")



    alarmSetup()
    setContentOverlayView()
    setContentClickTargets()
    watchState()
  }

  private fun setContentOverlayView() {
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
  }


  private fun watchState() {
    with(CoroutineScope(Dispatchers.Default)) {
      launch {
        deriveFullscreenVisibleFlow().collect { showFullscreen ->
          addOrRemoveView(fullscreenOverlay, showFullscreen)
        }
      }

      launch {
        addOrRemoveClickTargetView(countdownIsVisible, countdownClickTarget)
      }
    }
  }

  private suspend fun addOrRemoveView(viewHolder: OverlayViewHolder, isVisible: Boolean) {
    withContext(Dispatchers.Main) {
      when (isVisible) {
        true -> {
          windowManager.addView(viewHolder.view, viewHolder.params)
        }
        false -> {
          // wrap in try catch???
          windowManager.removeView(viewHolder.view)
          viewHolder.view.disposeComposition()
        }
      }
    }
  }

  private fun deriveFullscreenVisibleFlow(): Flow<Boolean> {
    // todo
//    return incrementIsVisible.combine(decrementIsVisible) { b1, b2 ->
//      if (b1 == null && b2 == null) {
//        null
//      } else {
//        listOf(b1, b2).contains(true)
//      }
//    }.filterNotNull().distinctUntilChanged()
    return countdownIsVisible.filterNotNull().distinctUntilChanged()
  }

  private suspend fun addOrRemoveClickTargetView(
    isVisible: Flow<Boolean?>, viewHolder: OverlayViewHolder
  ) {
    isVisible.filterNotNull().collect {
      addOrRemoveView(viewHolder, it)
    }
  }


  private fun setContentClickTargets() {
    countdownClickTarget.view.setContent {
      ClickTarget(service.state, this, countdownState.overlayState, countdownClickTarget) {
        logd("click target onclick")
        when (countdownState.timerState.value) {
          is TimerStatePaused -> {
            countdownState.timerState.value = TimerStateRunning
          }
          is TimerStateRunning -> {
            countdownState.timerState.value = TimerStatePaused
          }
          is TimerStateFinished -> {
            service.alarmController.player.pause()
            countdownState.resetTimerState(countdownState.durationSeconds)
          }
        }
      }
    }
  }


  private fun alarmSetup() {
    val context = service
    CoroutineScope(Dispatchers.Default).launch {
      countdownState.timerState.collect { timerState ->

        when (timerState) {
          TimerStateRunning -> {
            // set alarm
            logd("todo: run the timer")
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            // todo do i need to save pendingAlarm to state???
            pendingAlarm = PendingIntent.getBroadcast(
              context.applicationContext,
              REQUEST_CODE_PENDING_ALARM,
              intent,
              PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setAlarmClock(
              AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (countdownState.countdownSeconds * 1000), pendingAlarm
              ), pendingAlarm
            )
          }
          else -> {
            pendingAlarm?.cancel()
          }
        }
      }
    }
  }

  fun exitCountdown() {
    // what do i do ????
    // todo rewrite

    countdownState.overlayState.isVisible.value = false
    countdownState.overlayState.reset()
    countdownState.resetTimerState()

    service.alarmController.player.pause()
    pendingAlarm?.cancel()
    service.stopSelf()
  }

}