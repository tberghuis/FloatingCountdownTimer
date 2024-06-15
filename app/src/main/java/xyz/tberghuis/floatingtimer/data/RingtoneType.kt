package xyz.tberghuis.floatingtimer.data

import android.media.RingtoneManager.TYPE_ALARM
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.TYPE_RINGTONE

enum class RingtoneType(val type: Int) {
  ALARM(TYPE_ALARM),
  RINGTONE(TYPE_RINGTONE),
  NOTIFICATION(TYPE_NOTIFICATION),
}