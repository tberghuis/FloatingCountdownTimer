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
  var link by mutableStateOf("")
  var type by mutableStateOf("")
  var start by mutableStateOf("")
  var result by mutableStateOf("")

  fun processDataUri(data: Uri) {
    logd("data uri $data")
    link = data.toString()


    viewModelScope.launch {

    }



  }

}