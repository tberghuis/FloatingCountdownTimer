package xyz.tberghuis.floatingtimer.tmp.tmp01

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class DeepLinkScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  var link by mutableStateOf("")
}