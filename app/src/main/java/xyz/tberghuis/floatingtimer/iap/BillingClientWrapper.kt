package xyz.tberghuis.floatingtimer.iap

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
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

class BillingClientWrapper(
  context: Context,
) : PurchasesUpdatedListener {
  private var reconnectMilliseconds = RECONNECT_TIMER_START_MILLISECONDS
  private val connectedBillingClientStateFlow = MutableStateFlow<BillingClient?>(null)

  // future.txt rewrite with publishing purchasesUpdated BillingResult to SharedFlow???
  private var purchasesUpdatedContinuation: CancellableContinuation<BillingResult>? = null

  // do not use outside startConnection(), use provideBillingClient
  private val internalBillingClient = BillingClient.newBuilder(context)
    .setListener(this)
//    .enablePendingPurchases() // deprecated billingclient v7
    .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
    .build()

  // reference
  // https://github.com/android/play-billing-samples/tree/main/TrivialDriveKotlin
  private val billingClientStateListener = object : BillingClientStateListener {
    override fun onBillingServiceDisconnected() {
      connectedBillingClientStateFlow.value = null
      retryStartConnection()
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
      if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
        connectedBillingClientStateFlow.value = internalBillingClient
        reconnectMilliseconds = RECONNECT_TIMER_START_MILLISECONDS
      } else {
        retryStartConnection()
      }
    }
  }

  private fun retryStartConnection() {
    // Retries the billing service connection with exponential backoff
    CoroutineScope(IO).launch {
      delay(reconnectMilliseconds)
      // already connected
      if (connectedBillingClientStateFlow.value != null) {
        return@launch
      }
      internalBillingClient.startConnection(billingClientStateListener)
      reconnectMilliseconds = min(
        reconnectMilliseconds * 2,
        RECONNECT_TIMER_MAX_TIME_MILLISECONDS
      )
    }
  }

  override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
    try {
      if (purchasesUpdatedContinuation?.isActive == true) {
        purchasesUpdatedContinuation?.resume(billingResult)
      }
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
        connectedBillingClientStateFlow.value?.acknowledgePurchase(
          params
        ) { acknowledgePurchaseResult ->
          logd("acknowledgePurchaseResult $acknowledgePurchaseResult")
        }
      }
    }
  }

  private suspend fun provideBillingClient(): BillingClient {
    val bc = connectedBillingClientStateFlow.value
    if (bc != null) {
      return bc
    }
    internalBillingClient.startConnection(billingClientStateListener)
    return connectedBillingClientStateFlow.filterNotNull().first()
  }

  suspend fun checkPremiumPurchased(): Boolean? {
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
              if (purchases.any {
                  it.products.contains("halo_colour") || it.products.contains("premium_discount")
                }) {
                purchased = true
              }
            }

            else -> {
              // some sort of error
              purchased = null
            }
          }
          if (cont.isActive) {
            cont.resume(purchased)
          }
        }
      bc.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener)
    }
  }

  suspend fun purchaseProduct(activity: Activity, productId: String): BillingResult? {
    val productDetails = getProductDetails(productId) ?: return null
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

  suspend fun getProductDetails(productId: String): ProductDetails? {
    val pdList = getProductDetailsList(productId)
    return pdList.find {
      it.productId == productId
    }
  }

  private suspend fun getProductDetailsList(productId: String): List<ProductDetails> {
    val product = QueryProductDetailsParams.Product.newBuilder()
      .setProductId(productId)
      .setProductType(BillingClient.ProductType.INAPP)
      .build()
    val bc = provideBillingClient()
    val params = QueryProductDetailsParams.newBuilder()
      .setProductList(listOf(product))
      .build()
    return suspendCancellableCoroutine { cont ->
      logd("suspendCancellableCoroutine")
      val productDetailsResponseListener =
        ProductDetailsResponseListener { billingResult, productDetailsList ->
          if (cont.isActive) {
            cont.resume(productDetailsList)
          }
        }
      bc.queryProductDetailsAsync(params, productDetailsResponseListener)
    }
  }


  companion object {
    @Volatile
    private var instance: BillingClientWrapper? = null
    fun getInstance(context: Context) =
      instance ?: synchronized(this) {
        instance ?: BillingClientWrapper(context)
          .also { instance = it }
      }
  }


}

val Context.billingClientWrapper: BillingClientWrapper
  get() = BillingClientWrapper.getInstance(this)