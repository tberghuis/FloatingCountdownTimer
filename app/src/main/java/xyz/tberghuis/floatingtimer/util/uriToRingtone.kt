package xyz.tberghuis.floatingtimer.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

//fun uriToRingtone(context: Context, uri: Uri): Ringtone? {
//  return RingtoneManager.getRingtone(context, uri)
//}


suspend fun uriToRingtone(context: Context, uri: Uri): Ringtone? {
  return withContext(IO) {
    RingtoneManager.getRingtone(context, uri)
  }
}