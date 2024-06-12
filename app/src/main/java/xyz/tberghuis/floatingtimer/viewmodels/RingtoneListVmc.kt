package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.media.RingtoneManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import xyz.tberghuis.floatingtimer.data.RingtoneData
import xyz.tberghuis.floatingtimer.data.RingtoneType

class RingtoneListVmc(
  private val application: Application,
  private val ringtoneType: RingtoneType,
) {
  var ringtoneList by mutableStateOf(listOf<RingtoneData>())

  init {
    getRingtoneList()
  }

  private fun getRingtoneList() {
    val rl = mutableListOf<RingtoneData>()
    val manager = RingtoneManager(application)
    manager.setType(ringtoneType.type)
    val cursor = manager.cursor
    val count = cursor.count
    if (count > 0 && cursor.moveToFirst()) {
      do {
        val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        val uri = manager.getRingtoneUri(cursor.position)
        rl.add(RingtoneData(title, uri.toString()))
      } while (cursor.moveToNext())
      ringtoneList = rl
    }
  }
}