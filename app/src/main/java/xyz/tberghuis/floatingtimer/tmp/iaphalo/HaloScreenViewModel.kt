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
      val bds = BillingDataSource(application) {
        preferencesRepository.haloColourPurchased()
      }
      bds.startBillingConnection()
      bds.checkHaloColourPurchased()
      bds.endBillingConnection()
    }
  }

  fun purchaseHaloColourChange(activity: Activity) {
    viewModelScope.launch(IO) {
      val bds = BillingDataSource(application) {
        preferencesRepository.haloColourPurchased()
      }
      bds.startBillingConnection()
      val br = bds.purchaseHaloColourChange(activity)
      logd("purchaseHaloColourChange after br $br")
      // doitwrong: this is inefficient
      bds.checkHaloColourPurchased()
      // for some reason Terminating connection logs before acknowledge purchase???
      bds.endBillingConnection()
    }
  }
}