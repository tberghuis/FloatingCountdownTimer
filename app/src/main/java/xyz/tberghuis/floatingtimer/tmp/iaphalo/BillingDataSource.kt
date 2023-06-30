package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import xyz.tberghuis.floatingtimer.logd


// this does my head in
// what am i doing with my life

class BillingDataSource(
  private val context: Context
) {


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


    // end connection
    billingClient.endConnection()

    return false

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

private suspend fun isHaloColourPurchased(billingClient: BillingClient): Boolean =
  suspendCoroutine { continuation ->
    // copy from queryPurchases

  }