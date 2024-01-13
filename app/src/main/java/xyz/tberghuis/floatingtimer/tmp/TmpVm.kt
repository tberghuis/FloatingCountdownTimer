package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

// why no compiler error "application" ??? was happening in NoteBoat
class TmpVm(private val application: Application) : AndroidViewModel(application) {

  var progress by mutableFloatStateOf(0f)

}