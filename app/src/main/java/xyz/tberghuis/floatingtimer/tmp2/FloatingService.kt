package xyz.tberghuis.floatingtimer.tmp2

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.tberghuis.floatingtimer.logd


class FloatingService : LifecycleService(), SavedStateRegistryOwner {
  private val job = SupervisorJob()

  // Main.immediate to prevent ANRs user input...
  val scope = CoroutineScope(Dispatchers.Default + job)
  lateinit var overlayController: OverlayController

  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  private val binder = LocalBinder()
  inner class LocalBinder : Binder() {
    fun getService(): FloatingService = this@FloatingService
  }
  override fun onBind(intent: Intent): IBinder {
    super.onBind(intent)
    logd("onbind")
    return binder
  }


  override fun onCreate() {
    super.onCreate()
    ScreenEz.with(this.applicationContext)

    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)

    overlayController = OverlayController(this)
  }


}