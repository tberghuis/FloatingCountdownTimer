package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository


class HaloScreenViewModel(application: Application) : AndroidViewModel(application) {
//  var haloColourPurchased = false

  private val preferencesRepository = providePreferencesRepository(application)

  val haloColourPurchasedFlow get() = preferencesRepository.haloColourPurchasedFlow

  private val bds = BillingDataSource(application)

  init {
    viewModelScope.launch(IO) {

    }
  }

  fun changeHaloColour() {

    var haloColourPurchased = false

    viewModelScope.launch(IO) {
      haloColourPurchased = bds.checkHaloColourPurchased()
      if(haloColourPurchased){
        preferencesRepository.haloColourPurchased()
      }
    }

/////////////////////////////

    if (haloColourPurchased) {
      // show change color dialog
    } else {
//      val checkPurchased = billingclientwrapper.check_purchased
//      if(checkPurchased){
//        // show change color dialog
//      }
//      else {
//        // show buy dialog
//      }
    }
  }


  fun purchaseHaloColourChange(activity: Activity) {
    viewModelScope.launch(IO) {
      bds.purchaseHaloColourChange(activity)
    }
  }
}