package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp2.FloatingService

class TmpVm(private val application: Application) : AndroidViewModel(application) {

  // before use if service not running bindFloatingService then filterNotNull().first()
  private val floatingService = MutableStateFlow<FloatingService?>(null)

  fun bindFloatingService() {
    val floatingServiceIntent = Intent(application, FloatingService::class.java)
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as FloatingService.LocalBinder
        floatingService.value = binder.getService()
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        floatingService.value = null
      }
    }
    application.startForegroundService(floatingServiceIntent)
    application.bindService(floatingServiceIntent, connection, 0)
  }


  fun addStopwatch() {
    floatingService.value?.overlayController?.addStopwatch()
  }


}