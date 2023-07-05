package xyz.tberghuis.floatingtimer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
  var showPurchaseDialog by mutableStateOf(false)
}