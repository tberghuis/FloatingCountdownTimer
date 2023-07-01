package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.logd

class HaloScreenViewModel(private val application: Application) : AndroidViewModel(application) {
  private val preferencesRepository = providePreferencesRepository(application)
  val haloColourPurchasedFlow get() = preferencesRepository.haloColourPurchasedFlow

  fun checkHaloColourPurchased() {
    viewModelScope.launch(IO) {
      val bds = BillingDataSource(application)
      bds.startBillingConnection()
      checkHaloColourPurchased(bds)
      bds.endBillingConnection()
    }
  }

  fun purchaseHaloColourChange(activity: Activity) {
    viewModelScope.launch(IO) {
      val bds = BillingDataSource(application)
      bds.startBillingConnection()
      bds.purchaseHaloColourChange(activity)
      logd("purchaseHaloColourChange after")
      // doitwrong: this is inefficient
      checkHaloColourPurchased(bds)
      bds.endBillingConnection()
    }
  }

  private suspend fun checkHaloColourPurchased(billingDataSource: BillingDataSource) {
    // todo snackbar when errors
    // such as airplane mode
    // use CheckHaloColourPurchasedResult interface
    val purchased = billingDataSource.checkHaloColourPurchased()
    preferencesRepository.updateHaloColourPurchased(purchased)
  }

}