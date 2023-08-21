package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class SettingsViewModel(private val application: Application) : AndroidViewModel(application) {
  var showPurchaseDialog by mutableStateOf(false)

  // future.txt nullable and a loading spinner
  var haloColorChangePriceText by mutableStateOf("")
    private set

  private val preferences = application.providePreferencesRepository()
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
        val details = client.getHaloColourProductDetails()?.oneTimePurchaseOfferDetails
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

    // future.txt exit running timers because buy method will not work....
    // only if complaints

    viewModelScope.launch {
      BillingClientWrapper.run(application) { client ->
        val billingResult = client.purchaseHaloColourChange(activity)
        // todo error snackbar
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