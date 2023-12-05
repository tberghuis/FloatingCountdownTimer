package xyz.tberghuis.floatingtimer.service

import android.util.Log

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