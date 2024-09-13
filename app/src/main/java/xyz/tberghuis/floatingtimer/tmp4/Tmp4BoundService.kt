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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.logd
import kotlin.reflect.KClass

interface ServiceBinder<T : Service> {
  fun getService(): T
}

private class Tmp4BoundService<T : Service> private constructor(
  private val application: Application,
  private val serviceClass: KClass<T>
) {

  private val service = MutableStateFlow<T?>(null)
  var boundServiceDeferred: Deferred<T>? = null

  val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, binder: IBinder) {
      // to prevent ANR does this need to happen off Main dispatcher???
      service.value = (binder as ServiceBinder<T>).getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      service.value = null
    }

    override fun onBindingDied(name: ComponentName?) {
      super.onBindingDied(name)
      service.value = null
    }
  }

  init {
    logd("BoundService init - singleton")
  }

  private fun bindService() {
    val intent = Intent(application, serviceClass::class.java)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)
  }

  // doitwrong
  // do not keep reference to FloatingService anywhere
  // always use provide function
  suspend fun provideService(/* refresh bool, manual invoke by user */): T {
    boundServiceDeferred?.let {
      return it.await()
    }
    boundServiceDeferred = CoroutineScope(Default).async {
      bindService()
      service.filterNotNull().first()
    }
    return boundServiceDeferred!!.await()
  }
}
