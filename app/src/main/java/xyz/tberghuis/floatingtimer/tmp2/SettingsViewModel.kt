package xyz.tberghuis.floatingtimer.tmp2

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class SettingsViewModel(private val application: Application) : AndroidViewModel(application) {
  private val preferences = application.providePreferencesRepository()
  var colorPickerColorState = mutableStateOf(HsvColor.from(Color.Blue))

  init {
    viewModelScope.launch {
      colorPickerColorState.value = HsvColor.from(preferences.haloColourFlow.first())
    }
  }

  fun saveHaloColor() {
    viewModelScope.launch {
      preferences.updateHaloColour(colorPickerColorState.value.toColor())
    }
  }
}