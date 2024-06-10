package xyz.tberghuis.floatingtimer.tmp5

import android.media.RingtoneManager.TYPE_ALARM
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.TYPE_RINGTONE

enum class TmpRingtoneType(val type: Int) {
  ALARM(TYPE_ALARM),
  RINGTONE(TYPE_NOTIFICATION),
  NOTIFICATION(TYPE_RINGTONE),
}