package xyz.tberghuis.floatingtimer.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

fun uriToRingtone(context: Context, uri: Uri): Ringtone? {
  return RingtoneManager.getRingtone(context, uri)
}