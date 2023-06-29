package xyz.tberghuis.floatingtimer.tmp.iap

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class IapDemoVm(application: Application) : AndroidViewModel(application) {

  private val billingClientWrapper: BillingClientWrapper = BillingClientWrapper(application)

//  var productDetails: ProductDetails? = null


  fun startBillingConnection() {
    viewModelScope.launch(IO) {
      billingClientWrapper.startBillingConnection()
    }
  }

  fun queryProductDetails() {
    viewModelScope.launch(IO) {
      billingClientWrapper.queryProductDetails()
    }
  }

  fun queryPurchases() {
    viewModelScope.launch(IO) {
      billingClientWrapper.queryPurchases()
    }
  }

  fun launchBillingFlow(activity: Activity) {
    viewModelScope.launch(IO) {
      val productDetailsMap = billingClientWrapper.productWithProductDetails.value
      val productDetails = productDetailsMap["halo_colour"]
      billingClientWrapper.launchBillingFlow(activity, productDetails!!)
    }
  }
}