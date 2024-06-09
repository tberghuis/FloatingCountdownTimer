package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TmpCurrentRingtoneVmc(
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
          ringtoneFromUri(application, uri)?.getTitle(application)
        } ?: ""
      }
    }
  }
}

fun ringtoneFromUri(context: Context, uri: String): Ringtone? {
  return RingtoneManager.getRingtone(context, Uri.parse(uri))
}