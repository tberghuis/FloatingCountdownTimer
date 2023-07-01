package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Activity
import android.app.Application
import android.content.Context
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val billingClient = createBillingClient(context, purchasesUpdatedListener)
    // start connection

    val billingResult = startBillingConnection(billingClient)
    when (billingResult.responseCode) {
      BillingClient.BillingResponseCode.OK -> {}
      else -> {
        // todo show error to user???? snackbar
        return false
      }
    }
    logd("after when")

    // query product details
    val productDetails = getHaloColourProductDetails(billingClient)
    logd("productDetails $productDetails")

    val purchased = isHaloColourPurchased(billingClient, productDetails)


    // end connection
    billingClient.endConnection()

    return purchased

  }


  // todo return PurchaseHaloColourChangeResult
  // future.txt use arrow.kt Either
  suspend fun purchaseHaloColourChange(activity: Activity): Boolean =
    suspendCoroutine { continuation ->
      val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult: BillingResult, purchases: MutableList<Purchase>? ->
          logd("purchasesUpdatedListener")
          // todo
          continuation.resume(false)
        }
      val billingClient = createBillingClient(context, purchasesUpdatedListener)

      // todo make scope child Job of suspendCoroutine ???
      val scope = CoroutineScope(IO)

      scope.launch {
        val billingResult = startBillingConnection(billingClient)
        when (billingResult.responseCode) {
          BillingClient.BillingResponseCode.OK -> {}
          else -> {
            // todo show error to user????
            continuation.resume(false)
          }
        }
        logd("after when")

        // get product details

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


fun createBillingClient(
  context: Context,
  purchasesUpdatedListener: PurchasesUpdatedListener
): BillingClient {
  return BillingClient.newBuilder(context)
    .setListener(purchasesUpdatedListener)
    .enablePendingPurchases()
    .build()
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