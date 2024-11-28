package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Activity
import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.globalKtorClient
import xyz.tberghuis.floatingtimer.iap.billingClientWrapper
import xyz.tberghuis.floatingtimer.logd

class PremiumScreenVm(private val application: Application) : AndroidViewModel(application) {
  private val bcw = application.billingClientWrapper
  private val preferences = application.preferencesRepository

  val premiumPurchasedStateFlow =
    application.preferencesRepository.haloColourPurchasedFlow.stateIn(
      viewModelScope,
      SharingStarted.Eagerly, null
    )

  val snackbarHostState = SnackbarHostState()

  var discountCode by mutableStateOf("")
  var discountCodeError by mutableStateOf(false)
  var premiumPriceText by mutableStateOf("")
  var discountPriceText by mutableStateOf("")
  var validDiscountCode by mutableStateOf(false)

  init {
    getPremiumPrices()
  }

  private fun getPremiumPrices() {
    logd("getPremiumPrices")
    viewModelScope.launch {
      val otpoDetails = bcw.getProductDetails("halo_colour")?.oneTimePurchaseOfferDetails
      premiumPriceText = otpoDetails?.formattedPrice ?: ""
    }
    viewModelScope.launch {
      val otpoDetails = bcw.getProductDetails("premium_discount")?.oneTimePurchaseOfferDetails
      discountPriceText = otpoDetails?.formattedPrice ?: ""
      logd("discountPriceText $discountPriceText")
    }
  }

  fun applyDiscountCode(focusManager: FocusManager) {
    viewModelScope.launch {
      if (discountPriceText == "")
        getPremiumPrices()
    }

    viewModelScope.launch {
      when (isValidDiscountCode(discountCode)) {
        true -> {
          validDiscountCode = true
          focusManager.clearFocus()
          snackbarHostState.showSnackbar(application.getString(R.string.discount_code_applied))
        }

        false -> {
          discountCodeError = true
        }

        null -> {}
      }
    }
  }

  fun buy(activity: Activity) {
    viewModelScope.launch(IO) {
      val productId = if (validDiscountCode) "premium_discount" else "halo_colour"
      bcw.purchaseProduct(activity, productId)
      val purchased = bcw.checkPremiumPurchased() ?: return@launch
      preferences.updateHaloColourPurchased(purchased)
    }
  }

  // doitwrong global variable
  private suspend fun isValidDiscountCode(
    code: String,
    ktorClient: HttpClient = globalKtorClient
  ): Boolean? {
    try {
      val response =
        ktorClient.get("https://tberghuis.dev/FloatingCountdownTimer/valid-discount-codes.json")
      val validCodes = response.body<List<String>>()
      return validCodes.contains(code.uppercase())
    } catch (e: Exception) {
      e.message?.let {
        snackbarHostState.showSnackbar(it)
      }
      logd("e $e")
      return null
    }
  }
}