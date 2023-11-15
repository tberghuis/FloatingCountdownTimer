package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp2.FloatingService
import xyz.tberghuis.floatingtimer.tmp2.PremiumVmc

class TmpVm(private val application: Application) : AndroidViewModel(application) {

  // before use if service not running bindFloatingService then filterNotNull().first()
  private val floatingService = MutableStateFlow<FloatingService?>(null)
  private var serviceStarted = false

//  var showDialog by mutableStateOf(false)
  val premiumVmc = PremiumVmc(application, viewModelScope)

  private fun bindFloatingService() {
    serviceStarted = true
    val floatingServiceIntent = Intent(application, FloatingService::class.java)
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as FloatingService.LocalBinder
        floatingService.value = binder.getService()
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        serviceStarted = false
        floatingService.value = null
      }
    }
    application.startForegroundService(floatingServiceIntent)
    application.bindService(floatingServiceIntent, connection, 0)
  }

  fun addStopwatch() {
    runFloatingServiceLambda {
      overlayController.addStopwatch()
    }
  }

  private fun runFloatingServiceLambda(lambda: FloatingService.() -> Unit) {
    if (!serviceStarted) {
      bindFloatingService()
    }
    viewModelScope.launch {
      floatingService.filterNotNull().first().let {
        it.lambda()
      }
    }
  }

  fun addCountdown() {
    runFloatingServiceLambda {
      overlayController.addCountdown()
    }
  }

  fun exitAll() {
    runFloatingServiceLambda {
      overlayController.exitAll()
    }
  }
}