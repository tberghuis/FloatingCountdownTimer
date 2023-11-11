package xyz.tberghuis.floatingtimer.tmp2

abstract class Bubble(private val service: FloatingService) {
  // todo move ViewHolder logic to here
  val viewHolder = TimerViewHolder(service)

// todo or MVI
//  fun exitBubble() {
//    try {
//      service.overlayController.windowManager.removeView(viewHolder.view)
//    } catch (e: IllegalArgumentException) {
//      Log.e("Stopwatch", "IllegalArgumentException $e")
//    }
//  }

}