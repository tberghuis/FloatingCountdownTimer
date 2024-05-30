package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_ALARM
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpRingtoneVm(private val application: Application) : AndroidViewModel(application) {
  fun getRingtoneList() {
    logd("getRingtoneList")

//    do not use this ACTION_RINGTONE_PICKER

    val manager = RingtoneManager(application)
    manager.setType(TYPE_ALARM)
    val cursor = manager.cursor
    val count = cursor.count
    if (count > 0 && cursor.moveToFirst()) {
      do {
        val titleColumnIndex = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        val uriColumnIndex = cursor.getString(RingtoneManager.URI_COLUMN_INDEX)
        val idColumnIndex = cursor.getString(RingtoneManager.ID_COLUMN_INDEX)
        logd("titleColumnIndex $titleColumnIndex uriColumnIndex $uriColumnIndex idColumnIndex $idColumnIndex")
      } while (cursor.moveToNext())
    }
  }
}