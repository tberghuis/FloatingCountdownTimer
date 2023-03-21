package xyz.tberghuis.floatingtimer.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.di.dataStore
import xyz.tberghuis.floatingtimer.logd

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
  var minutes = mutableStateOf(TextFieldValue("0"))
  var seconds = mutableStateOf(TextFieldValue("0"))

  var showGrantOverlayDialog by mutableStateOf(false)

  fun promptNotificationPermission(context: Context, prompt: () -> Unit) {
    viewModelScope.launch {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // check if first run, to determine if to prompt
        val firstRun = context.dataStore.data.map { preferences ->
          preferences[booleanPreferencesKey("first_run")]
        }.first()
        if (firstRun == false) {
          return@launch
        }

        // check if granted permission
        val permission = ContextCompat.checkSelfPermission(
          context, Manifest.permission.POST_NOTIFICATIONS
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
          prompt()
        }

        logd("after launch")

        // set first_run to false
        context.dataStore.edit { preferences ->
          preferences[booleanPreferencesKey("first_run")] = false
        }
      }
    }
  }
}