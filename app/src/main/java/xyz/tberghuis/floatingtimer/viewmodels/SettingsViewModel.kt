package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.logd

class SettingsViewModel(private val application: Application) : AndroidViewModel(application) {
  var showPurchaseDialog by mutableStateOf(false)

  var haloColorChangePriceText by mutableStateOf("LOADING")
    private set

  private val preferencesRepository = providePreferencesRepository(application)
  var haloColourPurchased = false
    private set

  init {
    viewModelScope.launch {
      preferencesRepository.haloColourPurchasedFlow.collect {
        haloColourPurchased = it
      }
    }
    updateHaloColorChangePriceText()
  }

  fun updateHaloColorChangePriceText() {
    viewModelScope.launch {
      BillingClientWrapper.run(application) { client ->
        val details = client.getHaloColourProductDetails().oneTimePurchaseOfferDetails
        haloColorChangePriceText = details?.formattedPrice ?: ""
      }
    }
  }

}