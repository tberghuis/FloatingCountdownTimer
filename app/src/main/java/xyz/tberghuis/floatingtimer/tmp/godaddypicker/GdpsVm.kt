package xyz.tberghuis.floatingtimer.tmp.godaddypicker

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository


class GdpsVm(application: Application) : AndroidViewModel(application) {

  private val preferences = providePreferencesRepository(application)

//  val haloColourFlow get() = preferences.haloColourFlow

  // wrong initial color, meh
//  var colorPickerColor by mutableStateOf(HsvColor.from(Color.Blue))
  var colorPickerColorState = mutableStateOf(HsvColor.from(Color.Blue))

  init {
    viewModelScope.launch {
      preferences.haloColourFlow.collect {
        colorPickerColorState.value = HsvColor.from(it)
      }
    }
  }


  fun saveHaloColor() {
    viewModelScope.launch {
      preferences.updateHaloColour(colorPickerColorState.value.toColor())
    }
  }

    fun clearUserPrefs() {
    viewModelScope.launch {
      preferences.resetHaloColour()
    }
  }


}