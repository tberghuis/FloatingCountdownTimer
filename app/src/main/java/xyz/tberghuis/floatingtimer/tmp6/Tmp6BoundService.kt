package xyz.tberghuis.floatingtimer.tmp6

import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd

interface ServiceBinder<T : Service> {
  fun getService(): T
}

class Tmp6BoundService<T : Service>(
  private val application: Application,
  private val serviceClass: Class<T>
) {
  private val service = MutableStateFlow<T?>(null)
  private var job: Job? = null

  private val serviceFlow = callbackFlow {
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, binder: IBinder) {
        logd("onServiceConnected")
        trySend((binder as ServiceBinder<T>).getService())
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        logd("onServiceDisconnected")
        trySend(null)
      }

      override fun onBindingDied(name: ComponentName?) {
        super.onBindingDied(name)
        logd("onBindingDied")
        trySend(null)
      }
    }
    val intent = Intent(application, serviceClass)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)
    awaitClose {
      logd("awaitClose")
      application.unbindService(connection)
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

    if (job == null) {
      job = CoroutineScope(IO).launch {
        serviceFlow.collect {
          service.value = it
          if (it == null) {
            job?.cancel()
            job = null
          }
        }
      }
    }

    return service.filterNotNull().first()
  }
}