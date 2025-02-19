package xyz.tberghuis.floatingtimer.tmp.tmp01

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class DeepLinkScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  var link by mutableStateOf("")

  fun processDataUri(data: Uri) {
    logd("data uri $data")
    link = data.toString()
  }

}