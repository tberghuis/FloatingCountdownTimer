package xyz.tberghuis.floatingtimer.tmp.iap

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import xyz.tberghuis.floatingtimer.logd

// should call this billing

class BillingClientWrapper(
  context: Context
) : PurchasesUpdatedListener, ProductDetailsResponseListener {
  private val billingClient = BillingClient.newBuilder(context)
    .setListener(this)
    .enablePendingPurchases()
    .build()

  override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
    logd("onPurchasesUpdated")
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
}
