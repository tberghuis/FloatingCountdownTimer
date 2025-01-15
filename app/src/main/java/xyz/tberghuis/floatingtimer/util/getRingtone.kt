package xyz.tberghuis.floatingtimer.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

// todo rename file uriToRingtone.kt

fun uriToRingtone(context: Context, uri: Uri): Ringtone? {
  return RingtoneManager.getRingtone(context, uri)
}
//
//fun String.toUri(): Uri {
//  return Uri.parse(this)
//}