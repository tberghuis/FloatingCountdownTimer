package xyz.tberghuis.floatingtimer

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.service.FloatingService

class BoundFloatingService(private val application: Application) {
  private val floatingService = MutableStateFlow<FloatingService?>(null)
  private var serviceStarted = false

  private fun bindFloatingService() {
    if (serviceStarted) {
      return
    }
    serviceStarted = true
    val floatingServiceIntent = Intent(application, FloatingService::class.java)
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as FloatingService.LocalBinder
        floatingService.value = binder.getService()
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        floatingService.value = null
      }

      override fun onBindingDied(name: ComponentName?) {
        super.onBindingDied(name)
        serviceStarted = false
      }
    }
    application.startForegroundService(floatingServiceIntent)
    application.bindService(floatingServiceIntent, connection, 0)
  }

  suspend fun provideFloatingService(): FloatingService {
    bindFloatingService()
    return floatingService.filterNotNull().first()
  }
}