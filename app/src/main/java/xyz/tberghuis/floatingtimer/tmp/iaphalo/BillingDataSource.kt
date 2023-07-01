package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Activity
import android.app.Application
import android.content.Context
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
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.logd

class BillingDataSource(
  context: Context,
  private val updateHaloColourPurchased: suspend () -> Unit = {}
) : PurchasesUpdatedListener {

  private val billingClient = BillingClient.newBuilder(context)
    .setListener(this)
    .enablePendingPurchases()
    .build()

  // doitwrong
  private var purchasesUpdatedContinuation: Continuation<BillingResult>? = null

  override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
    logd("purchasesUpdatedListener")
    if (billingResult.responseCode != BillingClient.BillingResponseCode.OK || purchases.isNullOrEmpty()) {
      purchasesUpdatedContinuation?.resume(billingResult)
      return
    }
    for (purchase in purchases) {
      acknowledgePurchase(purchase)
    }
    purchasesUpdatedContinuation?.resume(billingResult)
  }

  private fun acknowledgePurchase(purchase: Purchase) {
    if (!purchase.isAcknowledged) {
      val params = AcknowledgePurchaseParams.newBuilder()
        .setPurchaseToken(purchase.purchaseToken)
        .build()
      billingClient.acknowledgePurchase(
        params
      ) { billingResult ->
        logd("acknowledgePurchase billingResult $billingResult")
      }
    }
  }

  // todo endconnection

  suspend fun startBillingConnection(): BillingResult = suspendCoroutine { continuation ->
    val billingClientStateListener = object : BillingClientStateListener {
      override fun onBillingServiceDisconnected() {
        // todo
        // probably best to let the app crash if this called???
        logd("onBillingServiceDisconnected")
//        continuation.resumeWithException()
      }

      override fun onBillingSetupFinished(billingResult: BillingResult) {
        continuation.resume(billingResult)
      }
    }
    billingClient.startConnection(billingClientStateListener)
  }


  // rewrite with my own result type, or arrow either
  // allow me to show snackbars on error cases

  // startBillingConnection must be called first
  suspend fun checkHaloColourPurchased() {
    // query product details
    val purchased = isHaloColourPurchased(billingClient)
    if (purchased) {
      updateHaloColourPurchased()
    }
  }

  fun endBillingConnection() {
    logd("Terminating connection")
    billingClient.endConnection()
  }


  // todo return PurchaseHaloColourChangeResult
  // future.txt use arrow.kt Either
  suspend fun purchaseHaloColourChange(activity: Activity): BillingResult =
    suspendCoroutine { continuation ->
      purchasesUpdatedContinuation = continuation

      // todo make scope child Job of suspendCoroutine ???
      val scope = CoroutineScope(IO)

      scope.launch {
        val productDetails = getHaloColourProductDetails(billingClient)

        val productDetailsParams =
          BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails)
            .build()

        val params = BillingFlowParams.newBuilder()
          .setProductDetailsParamsList(listOf(productDetailsParams))
          .build()
        if (!billingClient.isReady) {
          logd("launchBillingFlow: BillingClient is not ready")
        }
        billingClient.launchBillingFlow(activity, params)
      }
    }
}

/////////////////////////////////////////////////////////

private fun acknowledgePurchase(billingClient: BillingClient, purchase: Purchase) {
  if (!purchase.isAcknowledged) {
    val params = AcknowledgePurchaseParams.newBuilder()
      .setPurchaseToken(purchase.purchaseToken)
      .build()
    billingClient.acknowledgePurchase(
      params
    ) { billingResult ->
      logd("acknowledgePurchase billingResult $billingResult")
    }
  }
}


private suspend fun getHaloColourProductDetails(billingClient: BillingClient): ProductDetails =
  suspendCoroutine { continuation ->
    val productId = "halo_colour"
    val product = QueryProductDetailsParams.Product.newBuilder()
      .setProductId(productId)
      .setProductType(BillingClient.ProductType.INAPP)
      .build()

    val params = QueryProductDetailsParams.newBuilder().setProductList(listOf(product)).build()

    val productDetailsResponseListener =
      ProductDetailsResponseListener { billingResult, productDetailsList ->
        logd("productDetailsResponseListener")
        logd("billingResult $billingResult")
        logd("productDetailsList $productDetailsList")

        val productDetails = productDetailsList.find {
          it.productId == "halo_colour"
        }

        continuation.resume(productDetails!!)

      }
    billingClient.queryProductDetailsAsync(params, productDetailsResponseListener)
  }

private suspend fun isHaloColourPurchased(
  billingClient: BillingClient,
): Boolean =
  suspendCoroutine { continuation ->
    val queryPurchasesParams =
      QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()

    val purchasesResponseListener =
      PurchasesResponseListener { billingResult: BillingResult, purchases: MutableList<Purchase> ->
        logd("purchasesResponseListener")
        logd("billingResult $billingResult")
        logd("purchases $purchases")
        var purchased = false
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
            // TODO snackbar debugMessage
          }
        }
        continuation.resume(purchased)
      }
    billingClient.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener)
  }