package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.logd

class TmpSavedTimerDialogVmc(
  private val application: Application,
  private val scope: CoroutineScope
) {
  // stores SavedCountdown or SavedStopwatch of saved timer long press
  // close dialog = null
  var showOptionsDialog by mutableStateOf<SavedTimer?>(null)
  var showLinkDialog by mutableStateOf<SavedTimer?>(null)

  var start by mutableStateOf(true)

  fun deepLinkToClipboard(
    clipboardManager: ClipboardManager
  ) {
//    assert(showLinkDialog != null)
    val deepLink = showLinkDialog?.toDeepLink(start).toString()
    clipboardManager.setText(AnnotatedString(deepLink))
    showLinkDialog = null
  }

  fun deleteSavedTimer() {
    logd("deleteSavedTimer")
    scope.launch(IO) {
      showOptionsDialog?.let { timer ->
        when (timer) {
          is SavedStopwatch -> {
            application.appDatabase.savedStopwatchDao().delete(timer)
          }

          is SavedCountdown -> {
            application.appDatabase.savedCountdownDao().delete(timer)
          }
        }
        showOptionsDialog = null
      }
    }
  }
}

fun SavedTimer.toDeepLink(start: Boolean): Uri {
  val type = when (this) {
    is SavedStopwatch -> {
      "stopwatch"
    }

    is SavedCountdown -> {
      "countdown"
    }

    else -> {
      throw RuntimeException("invalid saved timer type")
    }
  }
  val id = id.toString()
  return Uri.Builder().apply {
    scheme("floatingtimer")
    authority("floatingtimer")
    appendQueryParameter("type", type)
    appendQueryParameter("id", id)
    if (start) {
      appendQueryParameter("start", "1")
    }
  }.build()
}