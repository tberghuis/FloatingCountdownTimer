package xyz.tberghuis.floatingtimer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.ForegroundService
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.logd

class ExitReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("exit receiver")

    val exitIntent = Intent(context.applicationContext, ForegroundService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
    context.startForegroundService(exitIntent)
  }
}