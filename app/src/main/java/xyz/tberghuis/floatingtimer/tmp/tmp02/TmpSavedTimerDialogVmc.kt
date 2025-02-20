package xyz.tberghuis.floatingtimer.tmp.tmp02

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import xyz.tberghuis.floatingtimer.data.SavedTimer

class TmpSavedTimerDialogVmc {
  // stores SavedCountdown or SavedStopwatch of saved timer long press
  // close dialog = null
  var showOptionsDialog by mutableStateOf<SavedTimer?>(null)
  var showLinkDialog by mutableStateOf<SavedTimer?>(null)

}