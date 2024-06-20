package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
            ringtoneFromUri(application, uri)?.getTitle(application)
          } catch (e: SecurityException) {
            Log.e("CurrentRingtoneVmc", "SecurityException $e")
            null
          }
        } ?: ""
      }
    }
  }
}

fun ringtoneFromUri(context: Context, uri: String): Ringtone? {
  return RingtoneManager.getRingtone(context, Uri.parse(uri))
}