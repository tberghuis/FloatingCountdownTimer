package xyz.tberghuis.floatingtimer.service

import android.util.Log
import xyz.tberghuis.floatingtimer.tmp2.FloatingService
import xyz.tberghuis.floatingtimer.tmp2.TimerViewHolder

abstract class Bubble(private val service: FloatingService) {
  val viewHolder = TimerViewHolder(service)
  open fun exit() {
    try {
      service.overlayController.windowManager.removeView(viewHolder.view)
    } catch (e: IllegalArgumentException) {
      Log.e("Bubble", "IllegalArgumentException $e")
    }
  }
  abstract fun reset()
  abstract fun onTap()
}