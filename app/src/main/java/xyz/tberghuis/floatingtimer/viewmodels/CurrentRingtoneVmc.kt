package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.util.uriToRingtone

class CurrentRingtoneVmc(
  private val alarmRingtoneUriFlow: Flow<String?>, scope: CoroutineScope,
  application: Application
) {
  var currentRingtoneUri by mutableStateOf<String?>(null)
  var currentRingtoneTitle by mutableStateOf("")

  init {
    scope.launch {
      alarmRingtoneUriFlow.collect { uri ->
        currentRingtoneUri = uri
        currentRingtoneTitle = uri?.let {
          try {
            uriToRingtone(application, uri.toUri())?.getTitle(application)
          } catch (e: SecurityException) {
            Log.e("CurrentRingtoneVmc", "SecurityException $e")
            null
          } catch (e: NullPointerException) {
            Log.e("CurrentRingtoneVmc", "NullPointerException $e")
            null
          } catch (e: IllegalArgumentException) {
            Log.e("CurrentRingtoneVmc", "IllegalArgumentException $e")
            null
          }
        } ?: ""
      }
    }
  }
}