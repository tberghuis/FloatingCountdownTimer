package xyz.tberghuis.floatingtimer.tmp.tmp01

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd

class DeepLinkScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  var uiLink by mutableStateOf("")
  var uiType by mutableStateOf("")
  var uiStart by mutableStateOf("")
  var uiResult by mutableStateOf("")

  fun processDataUri(data: Uri) {
    logd("data uri $data")

    val type = data.getQueryParameter("type")
    val id = data.getQueryParameter("id")
    val start = data.getBooleanQueryParameter("start", false)

    uiLink = data.toString()

    if (type == null || id == null) {
      // todo error
      return
    }

    uiType = type
    uiStart = start.toString()

    viewModelScope.launch {


    }


  }

}