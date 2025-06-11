package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService

class TmpBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("TmpBroadcastReceiver onReceive")
    val intent = Intent(context, FloatingService::class.java)
    context.startService(intent)
  }
}