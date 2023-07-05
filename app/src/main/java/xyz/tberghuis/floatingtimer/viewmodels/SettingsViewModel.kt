package xyz.tberghuis.floatingtimer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.logd

class SettingsViewModel : ViewModel() {
  var showPurchaseDialog by mutableStateOf(false)


  fun changeTimerColor(navController: NavHostController) {
    logd("change timer color")
    // if not purchased (from user prefs)
    // show dialog
    showPurchaseDialog = true
    // else
    // nav halo_colour_change
  }

}