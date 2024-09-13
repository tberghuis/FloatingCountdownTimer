package xyz.tberghuis.floatingtimer.tmp4

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
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.FloatingService
import kotlin.reflect.KClass

interface ServiceBinder<T : Service> {
  fun getService(): T
}

class Tmp4BoundService<T : Service>(
  private val application: Application,
  private val serviceClass: Class<T>
) {

  private val service = MutableStateFlow<T?>(null)
  private var boundServiceDeferred: Deferred<T>? = null

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, binder: IBinder) {
      logd("onServiceConnected")
      // to prevent ANR does this need to happen off Main dispatcher???
      service.value = (binder as ServiceBinder<T>).getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      boundServiceDeferred = null
      service.value = null
    }

    override fun onBindingDied(name: ComponentName?) {
      super.onBindingDied(name)
      boundServiceDeferred = null
      service.value = null
    }
  }

  init {
    logd("BoundService init")
  }

  private fun bindService() {
    logd("bindService")
//    val intent = Intent(application, serviceClass::class.java)
    val intent = Intent(application, serviceClass)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)
  }

  // doitwrong
  // do not keep reference to FloatingService anywhere
  // always use provide function
  suspend fun provideService(/* refresh bool, manual invoke by user */): T {
    logd("provideService")
    boundServiceDeferred?.let {
      return it.await()
    }
    boundServiceDeferred = CoroutineScope(IO).async {
      bindService()
      service.filterNotNull().first()
    }
    return boundServiceDeferred!!.await()
  }
}
