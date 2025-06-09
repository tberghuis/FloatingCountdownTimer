package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.tberghuis.floatingtimer.logd

class TmpBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == "com.example.snippets.ACTION_UPDATE_DATA") {
      val data = intent.getStringExtra("com.example.snippets.DATA") ?: "No data"
      logd("onReceive data $data")
    }
  }
}