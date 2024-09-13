package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService

private class Tmp4BoundService private constructor(private val application: Application) {
  private val myBtService = MutableStateFlow<FloatingService?>(null)
  var boundServiceDeferred: Deferred<FloatingService>? = null

  val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as FloatingService.LocalBinder
      // to prevent ANR does this need to happen off Main dispatcher???
      myBtService.value = binder.getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      myBtService.value = null
    }

    override fun onBindingDied(name: ComponentName?) {
      super.onBindingDied(name)
      myBtService.value = null
    }
  }

  init {
    logd("BoundService init - singleton")
  }

  private fun bindService() {
    val intent = Intent(application, FloatingService::class.java)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)
  }

  // doitwrong
  // do not keep reference to FloatingService anywhere
  // always use provide function
  suspend fun provideService(/* refresh bool, manual invoke by user */): FloatingService {
    boundServiceDeferred?.let {
      return it.await()
    }
    boundServiceDeferred = CoroutineScope(Default).async {
      bindService()
      myBtService.filterNotNull().first()
    }
    return boundServiceDeferred!!.await()
  }

  companion object {
    @Volatile
    private var INSTANCE: Tmp4BoundService? = null
    fun getInstance(context: Context): Tmp4BoundService {
      synchronized(this) {
        return INSTANCE ?: Tmp4BoundService(context.applicationContext as Application).also {
          INSTANCE = it
        }
      }
    }
  }

}
