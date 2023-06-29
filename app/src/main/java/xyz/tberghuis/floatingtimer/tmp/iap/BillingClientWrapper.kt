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

  override fun onProductDetailsResponse(p0: BillingResult, p1: MutableList<ProductDetails>) {
    logd("onProductDetailsResponse")
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
}
