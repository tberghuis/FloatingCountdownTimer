package xyz.tberghuis.floatingtimer.tmp4

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.tmp6.Tmp6BoundService

class Tmp4Vm(application: Application) : AndroidViewModel(application) {
  val boundService = Tmp6BoundService(application, TmpService::class.java)

  @SuppressLint("StaticFieldLeak")
  var service: TmpService? = null

  fun getService() {
    viewModelScope.launch {
      service = boundService.provideService()
    }
  }

  fun addTimer() {
    viewModelScope.launch {
      service?.overlayController?.addTimer()
    }
  }

  fun addTrash() {
    viewModelScope.launch {
      service?.overlayController?.trashController?.addTrashView()
    }
  }

  fun getTrashPosition() {
    viewModelScope.launch {
      service?.overlayController?.trashController?.getTrashPosition()
    }
  }


//  fun getServiceAndAddStopwatch() {
//    viewModelScope.launch {
//      boundService.provideService().overlayController.addStopwatch(
//        haloColor = Color.Blue,
//        timerShape = "circle",
//        label = null,
//        isBackgroundTransparent = false,
//      )
//    }
//  }


}