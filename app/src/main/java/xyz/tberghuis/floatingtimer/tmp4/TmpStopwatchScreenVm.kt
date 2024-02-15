package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TmpStopwatchScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {


  // move premiumVmc to sharedVm

  fun stopwatchButtonClick() {
//    viewModelScope.launch {
//      if (shouldShowPremiumDialog()) {
//        premiumVmc.showPurchaseDialog = true
//        return@launch
//      }
//      boundFloatingServiceVmc.provideFloatingService().overlayController.addStopwatch(
//        stopwatchHaloColor
//      )
//    }
  }

}