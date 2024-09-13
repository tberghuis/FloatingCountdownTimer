package xyz.tberghuis.floatingtimer.tmp4

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.FloatingService

class Tmp4Vm(application: Application) : AndroidViewModel(application) {
  val boundService = Tmp4BoundService<FloatingService>(application, FloatingService::class.java)

  @SuppressLint("StaticFieldLeak")
  var service: FloatingService? = null


  fun getService(){
    viewModelScope.launch {
      service = boundService.provideService()
    }
  }

}