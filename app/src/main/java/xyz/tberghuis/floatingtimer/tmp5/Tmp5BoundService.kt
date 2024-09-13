package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.ServiceBinder

class Tmp5BoundService<T : Service>(
  private val application: Application,
  private val serviceClass: Class<T>
) {

  //  private val service = MutableStateFlow<T?>(null)
  private var boundServiceDeferred: Deferred<T>? = null


  suspend fun createDeferred(): Deferred<T> {
    val scope = CoroutineScope(IO)
    val flow = flow {
      val intent = Intent(application, serviceClass)
      application.startForegroundService(intent)
      application.bindService(intent, object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
          logd("onServiceConnected")
          scope.launch {
            emit((binder as ServiceBinder<T>).getService())
          }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
          boundServiceDeferred = null
        }

        override fun onBindingDied(name: ComponentName?) {
          super.onBindingDied(name)
          boundServiceDeferred = null
        }
      }, 0)
    }
    return scope.async {
      flow.first()
    }
  }

  init {
    logd("BoundService init")
  }


  // doitwrong
  // do not keep reference to FloatingService anywhere
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
