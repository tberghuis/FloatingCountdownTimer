package xyz.tberghuis.floatingtimer.tmp4

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import kotlin.math.min

private const val RECONNECT_TIMER_START_MILLISECONDS = 1L * 1000L
private const val RECONNECT_TIMER_MAX_TIME_MILLISECONDS = 1000L * 60L * 15L // 15 minutes

class TmpBillingClientWrapper(
  private val context: Context,
  private val scope: CoroutineScope
) : PurchasesUpdatedListener {
  private var reconnectMilliseconds = RECONNECT_TIMER_START_MILLISECONDS

  private val billingClientStateFlow = MutableStateFlow<BillingClient?>(null)

  // this is probably bad practice
  // after read filterNotNull().first(), consume by setting to null.
  private val billingResultStateFlow = MutableStateFlow<BillingResult?>(null)


  override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
    billingResultStateFlow.value = billingResult
    if (billingResult.responseCode != BillingClient.BillingResponseCode.OK || purchases.isNullOrEmpty()) {
      return
    }
    for (purchase in purchases) {
      // acknowledge purchase
      if (!purchase.isAcknowledged) {
        val params = AcknowledgePurchaseParams.newBuilder()
          .setPurchaseToken(purchase.purchaseToken)
          .build()
        billingClientStateFlow.value?.acknowledgePurchase(
          params
        ) { acknowledgePurchaseResult ->
          logd("acknowledgePurchaseResult $acknowledgePurchaseResult")
        }
      }
    }
  }

  private suspend fun provideBillingClient(): BillingClient {
    if (billingClientStateFlow.value != null) {
      return billingClientStateFlow.value!!
    }
    val billingClient = BillingClient.newBuilder(context)
      .setListener(this)
      .enablePendingPurchases()
      .build()

    // reference
    // https://github.com/android/play-billing-samples/tree/main/TrivialDriveKotlin
    val billingClientStateListener = object : BillingClientStateListener {
      override fun onBillingServiceDisconnected() {
        billingClientStateFlow.value = null
        billingClient.startConnection(this)
      }

      override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
          billingClientStateFlow.value = billingClient
          reconnectMilliseconds = RECONNECT_TIMER_START_MILLISECONDS
        } else {
          val listener = this
          // Retries the billing service connection with exponential backoff
          scope.launch {
            delay(reconnectMilliseconds)
            billingClient.startConnection(listener)
            reconnectMilliseconds = min(
              reconnectMilliseconds * 2,
              RECONNECT_TIMER_MAX_TIME_MILLISECONDS
            )
          }
        }
      }
    }
    billingClient.startConnection(billingClientStateListener)
    return billingClientStateFlow.filterNotNull().first()
  }

  suspend fun getHaloColourProductDetails(): ProductDetails? {
    val channel = Channel<ProductDetails?>()
    val productId = "halo_colour"
    val product = QueryProductDetailsParams.Product.newBuilder()
      .setProductId(productId)
      .setProductType(BillingClient.ProductType.INAPP)
      .build()
    val params = QueryProductDetailsParams.newBuilder().setProductList(listOf(product)).build()
    val productDetailsResponseListener =
      ProductDetailsResponseListener { billingResult, productDetailsList ->
        logd("productDetailsResponseListener $billingResult $productDetailsList")
        val productDetails = productDetailsList.find {
          it.productId == "halo_colour"
        }
        scope.launch {
          channel.send(productDetails)
          channel.close()
        }
      }
    provideBillingClient().queryProductDetailsAsync(params, productDetailsResponseListener)
    return channel.receive()
  }

  suspend fun checkHaloColourPurchased(
  ): Boolean {
    TODO()
  }

  suspend fun purchaseHaloColourChange(activity: Activity): BillingResult? {
    TODO()
  }

}