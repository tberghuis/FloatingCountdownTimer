package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.lifecycle.AndroidViewModel

// why no compiler error "application" ??? was happening in NoteBoat
class TmpVm(private val application: Application) : AndroidViewModel(application) {
}