package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.ServiceBinder

class Tmp5BoundService<T : Service>(
  private val application: Application,
  private val serviceClass: Class<T>
) {
  private var boundServiceDeferred: Deferred<T>? = null

  private fun createDeferred(): Deferred<T> {
    val stateFlow = MutableStateFlow<T?>(null)
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, binder: IBinder) {
        logd("onServiceConnected")
        stateFlow.tryEmit((binder as ServiceBinder<T>).getService())
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        boundServiceDeferred = null
      }

      override fun onBindingDied(name: ComponentName?) {
        super.onBindingDied(name)
        boundServiceDeferred = null
      }
    }
    return CoroutineScope(IO).async {
      val intent = Intent(application, serviceClass)
      application.startForegroundService(intent)
      application.bindService(intent, connection, 0)
      stateFlow.filterNotNull().first()
    }
  }

  init {
    logd("BoundService init")
  }

  // doitwrong
  // do not keep reference to T:Service anywhere
  // always use provide function
  suspend fun provideService(/* refresh bool, manual invoke by user */): T {
    logd("provideService")
    boundServiceDeferred?.let {
      return it.await()
    }
    return createDeferred().also {
      boundServiceDeferred = it
    }.await()
  }
}