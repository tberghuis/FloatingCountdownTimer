package xyz.tberghuis.floatingtimer.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository


class HomeViewModel(application: Application) : AndroidViewModel(application) {

  private val preferencesRepository = application.providePreferencesRepository()

  var minutes = mutableStateOf(TextFieldValue("0"))
  var seconds = mutableStateOf(TextFieldValue("0"))

  var showGrantOverlayDialog by mutableStateOf(false)

  fun promptNotificationPermission(context: Context, prompt: () -> Unit) {
    viewModelScope.launch {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // check if first run, to determine if to prompt
        val firstRun = preferencesRepository.checkFirstRun()
        if (!firstRun) {
          return@launch
        }
        val permission = ContextCompat.checkSelfPermission(
          context, Manifest.permission.POST_NOTIFICATIONS
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
          prompt()
        }
        preferencesRepository.setFirstRunFalse()
      }
    }
  }

  val vibrationFlow = preferencesRepository.vibrationFlow
  fun updateVibration(vibration: Boolean) {
    logd("updateVibration $vibration")
    viewModelScope.launch {
      preferencesRepository.updateVibration(vibration)
    }
  }

  val soundFlow = preferencesRepository.soundFlow
  fun updateSound(sound: Boolean) {
    viewModelScope.launch {
      preferencesRepository.updateSound(sound)
    }
  }
}