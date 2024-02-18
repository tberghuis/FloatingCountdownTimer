package xyz.tberghuis.floatingtimer.tmp4

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import xyz.tberghuis.floatingtimer.logd
import kotlin.coroutines.resume
import kotlin.math.min

private const val RECONNECT_TIMER_START_MILLISECONDS = 1L * 1000L
private const val RECONNECT_TIMER_MAX_TIME_MILLISECONDS = 1000L * 60L * 15L // 15 minutes

class TmpBillingClientWrapper(
  private val context: Context,
  private val scope: CoroutineScope
) : PurchasesUpdatedListener {
  private var reconnectMilliseconds = RECONNECT_TIMER_START_MILLISECONDS

  private val billingClientStateFlow = MutableStateFlow<BillingClient?>(null)

  private var purchasesUpdatedContinuation: CancellableContinuation<BillingResult>? = null

  override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
    try {
      purchasesUpdatedContinuation?.resume(billingResult)
    } catch (e: RuntimeException) {
      // this happens sometimes for some reason ???
      Log.e("BillingClientWrapper", "error: $e")
    } finally {
      purchasesUpdatedContinuation = null
    }

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
    val bc = provideBillingClient()
    return suspendCancellableCoroutine { cont ->
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
          cont.resume(productDetails)
        }
      bc.queryProductDetailsAsync(params, productDetailsResponseListener)
    }
  }

  suspend fun checkHaloColourPurchased(
  ): Boolean? {
    val bc = provideBillingClient()
    return suspendCancellableCoroutine { cont ->
      val queryPurchasesParams =
        QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()

      val purchasesResponseListener =
        PurchasesResponseListener { billingResult: BillingResult, purchases: MutableList<Purchase> ->
          logd("purchasesResponseListener $billingResult $purchases")
          var purchased: Boolean? = false
          when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
              for (purchase in purchases) {
                if (purchase.products.contains("halo_colour")) {
                  purchased = true
                  break
                }
              }
            }

            else -> {
              // some sort of error
              purchased = null
            }
          }
          cont.resume(purchased)
        }
      bc.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener)
    }
  }

  suspend fun purchaseHaloColourChange(activity: Activity): BillingResult? {
    val productDetails = getHaloColourProductDetails() ?: return null
    val bc = provideBillingClient()
    return suspendCancellableCoroutine { cont ->
      purchasesUpdatedContinuation = cont
      val productDetailsParams =
        BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails)
          .build()
      val params = BillingFlowParams.newBuilder()
        .setProductDetailsParamsList(listOf(productDetailsParams))
        .build()
      bc.launchBillingFlow(activity, params)
    }
  }
}