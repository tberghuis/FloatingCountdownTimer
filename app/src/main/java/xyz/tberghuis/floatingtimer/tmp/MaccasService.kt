package xyz.tberghuis.floatingtimer.tmp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import xyz.tberghuis.floatingtimer.logd

class MaccasService: Service() {
  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onCreate() {
    super.onCreate()

    logd("MaccasService oncreate")
  }
}