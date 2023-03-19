package xyz.tberghuis.floatingtimer.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_RESET
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService


class StopwatchExitReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("StopwatchExitReceiver onReceive")

    val exitIntent = Intent(context.applicationContext, FloatingService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_STOPWATCH_EXIT)
    context.startForegroundService(exitIntent)
  }
}

class StopwatchResetReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    logd("StopwatchResetReceiver onReceive")

    val resetIntent = Intent(context.applicationContext, FloatingService::class.java)
    resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_STOPWATCH_RESET)
    context.startForegroundService(resetIntent)
  }
}