package xyz.tberghuis.floatingtimer.tmp2

import android.content.Context
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.logd


class OverlayController(val service: FloatingService) {
  val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

  val timerOverlaySet = mutableSetOf<Stopwatch>()


  fun addStopwatch() {
    logd("OverlayController addStopwatch")
    Stopwatch(service).also {
      timerOverlaySet.add(it)
      it.viewHolder.view.setContent {
        // todo compositionlocal it
//        StopwatchView(it.stopwatchState)
      }
//      clickTargetSetOnTouchListener(windowManager, it.viewHolder, it.overlayState)
      windowManager.addView(it.viewHolder.view, it.viewHolder.params)
    }
  }


}