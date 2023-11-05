package xyz.tberghuis.floatingtimer.tmp2

import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class FloatingService : LifecycleService(), SavedStateRegistryOwner {
    private val job = SupervisorJob()
  // Main.immediate to prevent ANRs user input...
  val scope = CoroutineScope(Dispatchers.Default + job)
  lateinit var overlayController: OverlayController

  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry


  override fun onCreate() {
    super.onCreate()
    ScreenEz.with(this.applicationContext)

    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)

    overlayController = OverlayController(this)
  }


}