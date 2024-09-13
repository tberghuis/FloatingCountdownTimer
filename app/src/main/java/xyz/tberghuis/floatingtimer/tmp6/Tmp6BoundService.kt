package xyz.tberghuis.floatingtimer.tmp6

import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.tmp4.ServiceBinder

fun tmplog(s: String) = Log.d("111", s)


class Tmp6BoundService<T : Service>(
  private val application: Application,
  private val serviceClass: Class<T>
) {
  private val service = MutableStateFlow<T?>(null)
  var job: Job? = null

  private val serviceFlow = callbackFlow {
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, binder: IBinder) {
        tmplog("onServiceConnected")
        trySend((binder as ServiceBinder<T>).getService())
      }

      override fun onServiceDisconnected(arg0: ComponentName) {
        tmplog("onServiceDisconnected")
        trySend(null)
      }

      override fun onBindingDied(name: ComponentName?) {
        super.onBindingDied(name)
        tmplog("onBindingDied")
        trySend(null)
      }
    }
    val intent = Intent(application, serviceClass)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)
    awaitClose {
      tmplog("awaitClose")
      application.unbindService(connection)
    }
  }

  init {
    tmplog("BoundService init")
  }

  // doitwrong
  // do not keep reference to T:Service anywhere
  // always use provide function
  suspend fun provideService(/* refresh bool, manual invoke by user */): T {
    tmplog("provideService")

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