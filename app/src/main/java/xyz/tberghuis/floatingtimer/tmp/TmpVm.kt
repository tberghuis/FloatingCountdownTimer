package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpVm(private val application: Application) : AndroidViewModel(application) {

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as TmpService.LocalBinder
      val tmpService = binder.getService()
      logd("onServiceConnected tmpService $tmpService")
      tmpService.tmpOverlayController.addComposeView()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
    }
  }

  fun bindService() {
    logd("bindService")
    val tmpServiceIntent = Intent(application, TmpService::class.java)
    application.startForegroundService(tmpServiceIntent)
    application.bindService(tmpServiceIntent, connection, 0)
  }
}