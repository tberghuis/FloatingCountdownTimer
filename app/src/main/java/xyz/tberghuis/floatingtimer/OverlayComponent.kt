package xyz.tberghuis.floatingtimer

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import xyz.tberghuis.floatingtimer.composables.TimerOverlay
import xyz.tberghuis.floatingtimer.events.onClickClickTargetOverlay
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.common.OverlayStateFDSFSDF
import xyz.tberghuis.floatingtimer.countdown.CountdownState

class OverlayComponent(
  private val context: Context,
  private val overlayState: OverlayStateFDSFSDF,
  private val countdownOverlayState: CountdownState,
  private val stopService: () -> Unit
) {
  // should i move this into service??? meh
  // put into an AlarmService class
  // inject with dagger / hilt
  val player: MediaPlayer

  val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  val density = context.resources.displayMetrics.density

  val timerSizePx = (TIMER_SIZE_DP * density).toInt()

  // doitwrong
  var isTimerOverlayShowing = false

  val clickTargetOverlay = OverlayViewHolder(
    WindowManager.LayoutParams(
      timerSizePx,
      timerSizePx,
      0, // todo place default position
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ), context
  )

  val fullscreenOverlay = OverlayViewHolder(
    WindowManager.LayoutParams(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ), context
  )

  init {
    val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    player = MediaPlayer.create(context, alarmSound)
    player.isLooping = true

    // https://developer.android.com/reference/android/view/WindowManager.LayoutParams#MaximumOpacity
    fullscreenOverlay.params.alpha = 1f
    val inputManager = context.getSystemService(INPUT_SERVICE) as InputManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
      fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
//      fullscreenOverlay.params.alpha = .5f
    }

    setContentClickTargetOverlay()
    setContentFullscreenOverlay()
  }

  fun showOverlay() {

//    clickTargetOverlay.view.windowToken ?: windowManager.addView(
//      clickTargetOverlay.view,
//      clickTargetOverlay.params
//    )
//
//    fullscreenOverlay.view.windowToken ?: windowManager.addView(
//      fullscreenOverlay.view,
//      fullscreenOverlay.params
//    )

    if (isTimerOverlayShowing) {
      return
    }

    windowManager.addView(clickTargetOverlay.view, clickTargetOverlay.params)
    windowManager.addView(fullscreenOverlay.view, fullscreenOverlay.params)
    isTimerOverlayShowing = true
  }

  // todo refactor, place in onDestroy???
  fun endService() {
    logd("endService")

    // put in a reset state function???

    // doitwrong
    player.pause()
//    countdownOverlayState.pendingAlarm?.cancel()

    overlayState.timerOffset = IntOffset.Zero
    clickTargetOverlay.params.x = 0
    clickTargetOverlay.params.y = 0

    windowManager.removeView(clickTargetOverlay.view)
    windowManager.removeView(fullscreenOverlay.view)

    // prevents multiple alarms playing TimerOverlay LaunchedEffect TimerFinished
    // i don't fully understand this fix...
    fullscreenOverlay.view.disposeComposition()
    clickTargetOverlay.view.disposeComposition()

    // is this needed
    isTimerOverlayShowing = false
    stopService()
  }

  // doitwrong refactor 1 aspect at a time
  private fun setContentClickTargetOverlay() {
    clickTargetOverlay.view.setContent {

      Box(modifier = Modifier
//          .background(Color.LightGray)
        .pointerInput(Unit) {
          detectDragGestures(onDragStart = {
            logd("clicktarget onDragStart")
            overlayState.showTrash = true
          }, onDrag = { change, dragAmount ->
//                change.consumeAllChanges()
            change.consume()
            val dragAmountIntOffset =
              IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
            val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
            var x = max(_timerOffset.x, 0)
            x = min(x, overlayState.screenWidthPx - timerSizePx)
            var y = max(_timerOffset.y, 0)
            y = min(y, overlayState.screenHeightPx - timerSizePx)
            overlayState.timerOffset = IntOffset(x, y)
          }, onDragEnd = {

            logd("onDragEnd")

            overlayState.showTrash = false

            // todo calc hover trash
            if (overlayState.isTimerHoverTrash) {
              endService()
              return@detectDragGestures
            }

            clickTargetOverlay.params.x = overlayState.timerOffset.x
            clickTargetOverlay.params.y = overlayState.timerOffset.y
            logd("onDragEnd x ${overlayState.timerOffset.x}")
            windowManager.updateViewLayout(clickTargetOverlay.view, clickTargetOverlay.params)
          })
        }
        .clickable {
          onClickClickTargetOverlay(player, countdownOverlayState)
        }) {
//        Text("click target")
      }
    }
  }

  private fun setContentFullscreenOverlay() {
    fullscreenOverlay.view.setContent {
      TimerOverlay(overlayState, player, countdownOverlayState)
    }
  }
}