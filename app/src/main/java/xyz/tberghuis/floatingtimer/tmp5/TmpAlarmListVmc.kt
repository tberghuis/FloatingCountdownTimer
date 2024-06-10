package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_ALARM
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TmpAlarmListVmc(private val application: Application) {

  var alarmList by mutableStateOf(listOf<TmpRingtoneData>())

  fun getAlarmList() {
    val al = mutableListOf<TmpRingtoneData>()
    val manager = RingtoneManager(application)
    manager.setType(TYPE_ALARM)
    val cursor = manager.cursor
    val count = cursor.count
    if (count > 0 && cursor.moveToFirst()) {
      do {
        val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        val uri = manager.getRingtoneUri(cursor.position)
        al.add(TmpRingtoneData(title, uri.toString()))
      } while (cursor.moveToNext())
      alarmList = al
    }
  }
}