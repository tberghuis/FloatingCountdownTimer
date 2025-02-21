package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.appDatabase

class TmpCountdownScreenVm(
  private val application: Application,
) :
  AndroidViewModel(application) {
  // this is fake CountdownScreenVm

  val savedTimerDialogVmc = TmpSavedTimerDialogVmc(application, viewModelScope)

  fun longPressStopwatch1() {
    val savedStopwatchDao = application.appDatabase.savedStopwatchDao()
    viewModelScope.launch {
      val stopwatch = savedStopwatchDao.getById(1)
      savedTimerDialogVmc.showOptionsDialog = stopwatch
    }
  }
}

@Preview
@Composable
fun TmpSavedTimerDialogDemo(
  vm: TmpCountdownScreenVm = viewModel()
) {
  // todo fake savedtimer on long press
  Column {
    Text("hello saved timer dialog demo")


    Button(onClick = {
      vm.longPressStopwatch1()
    }) {
      Text("long press stopwatch=1")
    }

  }



  SavedTimerOptionsDialog(vm.savedTimerDialogVmc, onDelete = {})
  SavedTimerLinkDialog(vm.savedTimerDialogVmc)
}