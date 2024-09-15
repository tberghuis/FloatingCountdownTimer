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
//      0,
//      0,
      590,
      825,
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

            trashController.trashState.isTimerHover.value =
              calcIsTimerHoverTrash(timerComposeView!!, trashController.trashComposeView!!)
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

  fun getTimerPosition() {
    val screen = IntArray(2)
    timerComposeView!!.getLocationOnScreen(screen)
    logd("getTimerPosition screen ${screen[0]} ${screen[1]}")
  }
}


fun calcIsTimerHoverTrash(timerView: ComposeView, trashView: ComposeView): Boolean {
  val timerWidthPx = 300
  val timerHeightPx = 300
  val trashWidthPx = 300
  val trashHeightPx = 300

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
