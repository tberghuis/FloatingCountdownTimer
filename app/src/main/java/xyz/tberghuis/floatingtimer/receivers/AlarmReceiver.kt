package xyz.tberghuis.floatingtimer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_COMPLETE
import xyz.tberghuis.floatingtimer.countdown.CountdownService
import xyz.tberghuis.floatingtimer.logd

class AlarmReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent?) {
    logd("onReceive start")
    logd("context $context")

    // todo call service method to play alarm and vibrate until user clicks stop
    sendCommandService(context)
  }

  private fun sendCommandService(context: Context) {
    val intent = Intent(context.applicationContext, CountdownService::class.java)
    // doitwrong
    intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_COUNTDOWN_COMPLETE)
    context.startForegroundService(intent)
  }
}