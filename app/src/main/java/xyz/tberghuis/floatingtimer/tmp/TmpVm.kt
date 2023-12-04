package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.tmp2.FloatingService
import xyz.tberghuis.floatingtimer.tmp2.PremiumVmc

class TmpVm(private val application: Application) : AndroidViewModel(application) {

  // before use if service not running bindFloatingService then filterNotNull().first()
  private val floatingService = MutableStateFlow<FloatingService?>(null)
  private var serviceStarted = false

  val premiumVmc = PremiumVmc(application, viewModelScope)

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
//        serviceStarted = false
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

  fun addStopwatch() {
    viewModelScope.launch {
      if (shouldShowPremiumDialog()) {
        premiumVmc.showPurchaseDialog = true
      } else {
        provideFloatingService().overlayController.addStopwatch()
      }
    }
  }

  fun addCountdown() {
    viewModelScope.launch {
      if (shouldShowPremiumDialog()) {
        premiumVmc.showPurchaseDialog = true
      } else {
        provideFloatingService().overlayController.addCountdown(10)
      }
    }
  }

  private suspend fun shouldShowPremiumDialog(): Boolean {
    val premiumPurchased =
      application.providePreferencesRepository().haloColourPurchasedFlow.first()
    val floatingService = provideFloatingService()
    return !premiumPurchased && floatingService.overlayController.getNumberOfBubbles() == 2
  }

  fun exitAll() {
    viewModelScope.launch {
      provideFloatingService().overlayController.exitAll()
    }
  }

  private suspend fun provideFloatingService(): FloatingService {
    // todo follow this pattern for BillingLibrary
    //  in my BillingLibraryWrapper
    bindFloatingService()
    return floatingService.filterNotNull().first()
  }
}