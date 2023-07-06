package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.logd

class SettingsViewModel(private val application: Application) : AndroidViewModel(application) {
  var showPurchaseDialog by mutableStateOf(false)

  var haloColorChangePriceText by mutableStateOf("LOADING")
    private set

  private val preferences = providePreferencesRepository(application)
  var haloColourPurchased = false
    private set

  var colorPickerColorState = mutableStateOf(HsvColor.from(Color.Blue))

  init {
    viewModelScope.launch {
      preferences.haloColourPurchasedFlow.collect {
        haloColourPurchased = it
      }
    }
    viewModelScope.launch {
      colorPickerColorState.value = HsvColor.from(preferences.haloColourFlow.first())
    }
    updateHaloColorChangePriceText()
  }

  // should call again when showPurchaseDialog if still equal LOADING
  fun updateHaloColorChangePriceText() {
    viewModelScope.launch {
      BillingClientWrapper.run(application) { client ->
        val details = client.getHaloColourProductDetails().oneTimePurchaseOfferDetails
        haloColorChangePriceText = details?.formattedPrice ?: ""
      }
    }
  }

  fun saveHaloColor() {
    viewModelScope.launch {
      preferences.updateHaloColour(colorPickerColorState.value.toColor())
    }
  }

  fun buy(activity: Activity) {
    showPurchaseDialog = false
    viewModelScope.launch {
      BillingClientWrapper.run(application) { client ->
        val billingResult = client.purchaseHaloColourChange(activity)
        // todo error

        val purchased = client.checkHaloColourPurchased()
        preferences.updateHaloColourPurchased(purchased)

        // assume user wants to save (they did click save button)
        if (purchased) {
          saveHaloColor()
        }
      }
    }
  }


}