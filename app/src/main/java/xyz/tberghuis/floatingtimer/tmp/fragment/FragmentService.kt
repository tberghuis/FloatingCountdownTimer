package xyz.tberghuis.floatingtimer.tmp.fragment

import android.app.Service
import android.content.Intent
import android.os.IBinder
import xyz.tberghuis.floatingtimer.logd


class FragmentService : Service() {

  override fun onCreate() {
    super.onCreate()

  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("FragmentService onStartCommand")

    return START_STICKY
  }


}