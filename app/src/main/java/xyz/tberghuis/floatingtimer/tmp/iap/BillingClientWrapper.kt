package xyz.tberghuis.floatingtimer.tmp.iap

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.tberghuis.floatingtimer.logd

// reference https://github.com/android/play-billing-samples
// PlayBillingCodelab
class BillingClientWrapper(
  context: Context
) : PurchasesUpdatedListener, ProductDetailsResponseListener {
  private val billingClient = BillingClient.newBuilder(context)
    .setListener(this)
    .enablePendingPurchases()
    .build()

  private val _productWithProductDetails =
    MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
  val productWithProductDetails =
    _productWithProductDetails.asStateFlow()

  private val _purchases =
    MutableStateFlow<List<Purchase>>(listOf())
  val purchases = _purchases.asStateFlow()

  override fun onPurchasesUpdated(
    billingResult: BillingResult,
    purchases: List<Purchase>?
  ) {
    logd("onPurchasesUpdated billingResult $billingResult purchases $purchases")
    when (billingResult.responseCode) {
      BillingClient.BillingResponseCode.OK -> {
        if (purchases.isNullOrEmpty()) {
          return
        }
        _purchases.value = purchases
        for (purchase in purchases) {
          acknowledgePurchase(purchase)
        }
      }

      BillingClient.BillingResponseCode.USER_CANCELED -> {
      }
    }
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

  override fun onProductDetailsResponse(
    billingResult: BillingResult,
    productDetailsList: MutableList<ProductDetails>
  ) {
    val responseCode = billingResult.responseCode
    val debugMessage = billingResult.debugMessage

    logd("onProductDetailsResponse responseCode $responseCode debugMessage $debugMessage")

    when (responseCode) {
      BillingClient.BillingResponseCode.OK -> {
        logd("productDetailsList $productDetailsList")
        productDetailsList.forEach {
          logd("ProductDetails: $it")
        }
        _productWithProductDetails.value = productDetailsList.associateBy {
          it.productId
        }
      }

      else -> {
        logd("onProductDetailsResponse: $responseCode $debugMessage")
      }
    }
  }


  // todo return a flow
  fun startBillingConnection() {
    billingClient.startConnection(object : BillingClientStateListener {
      override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
          logd("billing ok")

        } else {
          logd("billing not ok: ${billingResult.debugMessage}")
        }
      }

      override fun onBillingServiceDisconnected() {
        logd("Billing connection disconnected")
      }
    })
  }

  fun queryProductDetails() {
    val productId = "halo_colour"
    val product = QueryProductDetailsParams.Product.newBuilder()
      .setProductId(productId)
      .setProductType(BillingClient.ProductType.INAPP)
      .build()

    val params = QueryProductDetailsParams.newBuilder().setProductList(listOf(product)).build()

    billingClient.queryProductDetailsAsync(params, this)
  }

  fun queryPurchases() {
    if (!billingClient.isReady) {
      logd("queryPurchases: BillingClient is not ready")
    }
    billingClient.queryPurchasesAsync(
      QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
    ) { billingResult, purchaseList ->
      if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
        logd("purchaseList $purchaseList")
      } else {
        logd("billingResult.debugMessage: ${billingResult.debugMessage}")
      }
    }
  }


  fun launchBillingFlow(activity: Activity, productDetails: ProductDetails) {
    val productDetailsParams =
      BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build()

    val params = BillingFlowParams.newBuilder()
      .setProductDetailsParamsList(listOf(productDetailsParams))
      .build()
    if (!billingClient.isReady) {
      logd("launchBillingFlow: BillingClient is not ready")
    }
    billingClient.launchBillingFlow(activity, params)
  }


  fun terminateBillingConnection() {
    logd("Terminating connection")
    billingClient.endConnection()
  }

}
