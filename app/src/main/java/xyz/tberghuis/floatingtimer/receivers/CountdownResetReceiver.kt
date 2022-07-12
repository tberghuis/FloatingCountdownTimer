package xyz.tberghuis.floatingtimer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.ForegroundService
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.logd

class CountdownResetReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("CountdownResetReceiver onReceive")

    val resetIntent = Intent(context.applicationContext, ForegroundService::class.java)
    resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_RESET)
    context.startForegroundService(resetIntent)
  }
}