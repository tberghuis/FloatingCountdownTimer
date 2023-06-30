package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository


class HaloScreenViewModel(application: Application) : AndroidViewModel(application) {
  var haloColourPurchased = false

  val preferencesRepository = providePreferencesRepository(application)

  init {
    viewModelScope.launch(IO) {
      preferencesRepository.haloColourPurchasedFlow.collect {
        haloColourPurchased = it
      }
    }
  }

  fun changeHaloColour(){

    if(haloColourPurchased){
      // show change color dialog
    }
    else {
//      val checkPurchased = billingclientwrapper.check_purchased
//      if(checkPurchased){
//        // show change color dialog
//      }
//      else {
//        // show buy dialog
//      }
    }
  }
}