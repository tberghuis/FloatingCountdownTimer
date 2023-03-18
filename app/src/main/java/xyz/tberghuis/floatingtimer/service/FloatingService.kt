package xyz.tberghuis.floatingtimer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import xyz.tberghuis.floatingtimer.logd

class FloatingService : Service() {

  override fun onCreate() {
    super.onCreate()
    // todo
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("FloatingService onStartCommand")



    return START_STICKY
  }

}