package xyz.tberghuis.floatingtimer.tmp6

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpService

class TmpOverlayController(val service: TmpService) {
  var timerComposeView: ComposeView? = null
  var timerParams: WindowManager.LayoutParams? = null
  val timerState = TmpTimerState()

  val trashController = TmpTrashController(service)

  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  init {
    logd("TmpOverlayController")
  }

  @SuppressLint("ClickableViewAccessibility")
  fun addTimer() {

    timerParams = WindowManager.LayoutParams(
      300,
      300,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    ).apply {
      gravity = Gravity.TOP or Gravity.LEFT
    }

    timerComposeView = ComposeView(service).apply {
      setViewTreeSavedStateRegistryOwner(service)
      setViewTreeLifecycleOwner(service)


      setOnTouchListener { _, event ->
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
            timerState.paramStartDragX = timerParams!!.x
            timerState.paramStartDragY = timerParams!!.y
            timerState.startDragRawX = event.rawX
            timerState.startDragRawY = event.rawY
          }

          MotionEvent.ACTION_MOVE -> {
            trashController.trashState.isBubbleDragging.value = true
            timerParams!!.x =
              (timerState.paramStartDragX + (event.rawX - timerState.startDragRawX)).toInt()
            timerParams!!.y =
              (timerState.paramStartDragY + (event.rawY - timerState.startDragRawY)).toInt()
            // updateClickTargetParamsWithinScreenBounds
            windowManager.updateViewLayout(this, timerParams)
          }

          MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            trashController.trashState.isBubbleDragging.value = false
//            if (trashController.isBubbleHoveringTrash) {
//              trashController.isBubbleHoveringTrash = false
//              exitTimer()
//            }
          }

        }

        false
      }

    }

    timerComposeView!!.setContent {
      Box(
        modifier = Modifier.background(Color.Blue),
      ) {

      }
    }



    windowManager.addView(timerComposeView, timerParams)

  }


}