package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.provideBillingClientWrapper
import xyz.tberghuis.floatingtimer.providePreferencesRepository

// is there a better pattern to share code multiple viewmodels?
// doitwrong
class PremiumVmc(private val application: Application, private val scope: CoroutineScope) {
  var showPurchaseDialog by mutableStateOf(false)
  var premiumPriceText by mutableStateOf("")
    private set

  private val preferences = application.providePreferencesRepository()

  private val billingClientWrapper = application.provideBillingClientWrapper()

  init {
    updateHaloColorChangePriceText()
  }

  fun updateHaloColorChangePriceText() {
    scope.launch(IO) {
      val details = billingClientWrapper.getProductDetails("halo_colour")?.oneTimePurchaseOfferDetails
      // is this needed???
      withContext(Main) {
        premiumPriceText = details?.formattedPrice ?: ""
      }
    }
  }

  fun buy(activity: Activity, purchasedCallback: () -> Unit) {
    showPurchaseDialog = false
    scope.launch(IO) {
      // this awaits billingResult in onPurchasesUpdated
      billingClientWrapper.purchaseProduct(activity, "halo_colour")
      val purchased = billingClientWrapper.checkPremiumPurchased() ?: return@launch
      preferences.updateHaloColourPurchased(purchased)
      if (purchased) {
        // is the context switch needed???
        withContext(Main) {
          purchasedCallback()
        }
      }
    }
  }
}