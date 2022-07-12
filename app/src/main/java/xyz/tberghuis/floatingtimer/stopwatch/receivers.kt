package xyz.tberghuis.floatingtimer.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.logd


class StopwatchExitReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("StopwatchExitReceiver onReceive")

    val exitIntent = Intent(context.applicationContext, StopwatchService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
    context.startForegroundService(exitIntent)
  }
}

class StopwatchResetReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("StopwatchResetReceiver onReceive")

    val resetIntent = Intent(context.applicationContext, StopwatchService::class.java)
    resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_RESET)
    context.startForegroundService(resetIntent)
  }
}