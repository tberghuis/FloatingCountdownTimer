package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.RingtoneManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RingtoneListController(
  private val application: Application,
  private val ringtoneType: TmpRingtoneType,
) {
  var ringtoneList by mutableStateOf(listOf<TmpRingtoneData>())

  init {
    getRingtoneList()
  }

  private fun getRingtoneList() {
    val rl = mutableListOf<TmpRingtoneData>()
    val manager = RingtoneManager(application)
    manager.setType(ringtoneType.type)
    val cursor = manager.cursor
    val count = cursor.count
    if (count > 0 && cursor.moveToFirst()) {
      do {
        val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        val uri = manager.getRingtoneUri(cursor.position)
        rl.add(TmpRingtoneData(title, uri.toString()))
      } while (cursor.moveToNext())
      ringtoneList = rl
    }
  }
}