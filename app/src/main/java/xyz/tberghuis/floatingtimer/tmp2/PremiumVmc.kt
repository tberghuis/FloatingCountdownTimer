package xyz.tberghuis.floatingtimer.tmp2

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.providePreferencesRepository

// is there a better pattern to share code multiple viewmodels?
// doitwrong
class PremiumVmc(private val application: Application, private val scope: CoroutineScope) {
  var showPurchaseDialog by mutableStateOf(false)
  var haloColorChangePriceText by mutableStateOf("")
    private set

  private val preferences = application.providePreferencesRepository()

  init {
    updateHaloColorChangePriceText()
  }

  fun updateHaloColorChangePriceText() {
    scope.launch {
      BillingClientWrapper.run(application) { client ->
        val details = client.getHaloColourProductDetails()?.oneTimePurchaseOfferDetails
        haloColorChangePriceText = details?.formattedPrice ?: ""
      }
    }
  }

  fun buy(activity: Activity, purchasedCallback: () -> Unit) {
    showPurchaseDialog = false

    // future.txt exit running timers because buy method will not work....
    // only if complaints
    scope.launch {
      BillingClientWrapper.run(application) { client ->
        val billingResult = client.purchaseHaloColourChange(activity)
        // todo error snackbar
        val purchased = client.checkHaloColourPurchased()
        preferences.updateHaloColourPurchased(purchased)
        // assume user wants to save (they did click save button)
        if (purchased) {
          purchasedCallback()
        }
      }
    }
  }
}