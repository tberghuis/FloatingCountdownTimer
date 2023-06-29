package xyz.tberghuis.floatingtimer.tmp.iap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class IapDemoVm(application: Application) : AndroidViewModel(application) {

  private val billingClientWrapper: BillingClientWrapper = BillingClientWrapper(application)

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

}