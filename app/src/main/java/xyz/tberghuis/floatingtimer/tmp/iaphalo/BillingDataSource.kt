package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import xyz.tberghuis.floatingtimer.logd


// this does my head in
// what am i doing with my life

class BillingDataSource(
  private val context: Context
) {

  // rewrite with my own result type, or arrow either
  // allow me to show snackbars on error cases
  suspend fun checkHaloColourPurchased(): Boolean {
    // doitwrong
    // move reusable functions outside of class


    // not used....
    val purchasesUpdatedListener =
      PurchasesUpdatedListener { billingResult: BillingResult, purchases: MutableList<Purchase>? ->
        logd("purchasesUpdatedListener")
      }

    // todo, put in fun pass in purchasesUpdatedListener lambda
    val billingClient = BillingClient.newBuilder(context)
      .setListener(purchasesUpdatedListener)
      .enablePendingPurchases()
      .build()

    // start connection

    val billingResult = startBillingConnection(billingClient)
    when (billingResult.responseCode) {
      BillingClient.BillingResponseCode.OK -> {}
      else -> {
        // todo show error to user????
        return false
      }
    }
    logd("after when")

    // query product details
    val productDetails = getHaloColourProductDetails(billingClient)
    logd("productDetails $productDetails")

    if(productDetails == null){
      // todo some sort of message to user
      // snackbar
      return false
    }

    val purchased = isHaloColourPurchased(billingClient, productDetails)


    // end connection
    billingClient.endConnection()

    return purchased

  }

}


private suspend fun startBillingConnection(billingClient: BillingClient): BillingResult =
  suspendCoroutine { continuation ->
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


private suspend fun getHaloColourProductDetails(billingClient: BillingClient): ProductDetails? =
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

        continuation.resume(productDetails)

      }
    billingClient.queryProductDetailsAsync(params, productDetailsResponseListener)
  }

private suspend fun isHaloColourPurchased(
  billingClient: BillingClient,
  productDetails: ProductDetails
): Boolean =
  suspendCoroutine { continuation ->
    // copy from queryPurchases

    val queryPurchasesParams =
      QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()

    val purchasesResponseListener =
      PurchasesResponseListener { billingResult: BillingResult, purchases: MutableList<Purchase> ->
        logd("purchasesResponseListener")
        logd("billingResult $billingResult")
        logd("purchases $purchases")

        // todo
        // write purchase button first

        continuation.resume(false)

      }

    billingClient.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener)

  }