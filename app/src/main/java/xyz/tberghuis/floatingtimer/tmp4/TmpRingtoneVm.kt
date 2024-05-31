package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_ALARM
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd


class TmpRingtoneVm(private val application: Application) : AndroidViewModel(application) {


  var ringtone: Ringtone? = null

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

        val ringtoneURI = manager.getRingtoneUri(cursor.position)


        logd("titleColumnIndex $titleColumnIndex uriColumnIndex $uriColumnIndex idColumnIndex $idColumnIndex ringtoneURI $ringtoneURI")
      } while (cursor.moveToNext())
    }
  }

  fun getActualDefaultRingtoneUri() {
    val defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
      application,
      RingtoneManager.TYPE_ALARM
    )

    // getActualDefaultRingtoneUri file:///system/media/audio/ringtones/DigitalUniverse.ogg
    logd("getActualDefaultRingtoneUri $defaultRingtoneUri")

    ringtone = RingtoneManager.getRingtone(application, defaultRingtoneUri)
  }

  fun getDefaultUri() {
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    // getDefaultUri content://settings/system/alarm_alert
    logd("getDefaultUri $uri")

    ringtone = RingtoneManager.getRingtone(application, uri)
  }


  fun playRingtone() {
    ringtone?.play()
  }

  fun stopRingtone() {
    ringtone?.stop()
  }

  fun ringtoneGetTitle() {
    logd("ringtoneGetTitle ${ringtone?.getTitle(application)}")
  }

}