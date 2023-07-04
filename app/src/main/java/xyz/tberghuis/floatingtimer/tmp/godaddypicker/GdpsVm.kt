package xyz.tberghuis.floatingtimer.tmp.godaddypicker

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository


class GdpsVm(application: Application) : AndroidViewModel(application) {

  private val preferences = providePreferencesRepository(application)

  val haloColourFlow get() = preferences.haloColourFlow


  fun saveHaloColor(color: Color) {
    viewModelScope.launch {
      preferences.updateHaloColour(color)
    }
  }
}