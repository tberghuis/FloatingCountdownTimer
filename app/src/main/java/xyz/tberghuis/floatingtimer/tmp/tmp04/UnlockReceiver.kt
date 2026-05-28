package xyz.tberghuis.floatingtimer.tmp.tmp04

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.Bubble

class UnlockReceiver(
  private val bubbleSet: Set<Bubble>
) : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == Intent.ACTION_USER_PRESENT) {
      logd("UnlockReceiver onReceive")
      CoroutineScope(Dispatchers.Main).launch {
        // who knows what the best magic number is
        // for samsung S22 I need around 100ms delay
        // otherwise UI animation appears frozen
        delay(500)
        logd("bubbleSet composeView invalidate")
        bubbleSet.forEach { bubble ->
          bubble.viewHolder.view.invalidate()
        }
      }
    }
  }
}