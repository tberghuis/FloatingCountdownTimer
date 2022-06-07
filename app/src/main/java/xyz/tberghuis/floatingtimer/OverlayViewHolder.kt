package xyz.tberghuis.floatingtimer

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OverlayViewHolder(val params: WindowManager.LayoutParams, context: Context) {
  val view = ComposeView(context)

  init {
    params.gravity = Gravity.TOP or Gravity.LEFT
    // i don't think this matters
    view.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    composeViewConfigure(view)
  }

  // todo move fun here
//  private fun composeViewConfigure(){}
}

// TODO move into overlayviewholder
// https://gist.github.com/handstandsam/6ecff2f39da72c0b38c07aa80bbb5a2f
fun composeViewConfigure(composeView: ComposeView) {
  val lifecycleOwner = MyLifecycleOwner()
  lifecycleOwner.performRestore(null)
  lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  ViewTreeLifecycleOwner.set(composeView, lifecycleOwner)
  ViewTreeSavedStateRegistryOwner.set(composeView, lifecycleOwner)

  val viewModelStore = ViewModelStore()
  ViewTreeViewModelStoreOwner.set(composeView) { viewModelStore }

  val coroutineContext = AndroidUiDispatcher.CurrentThread
  val runRecomposeScope = CoroutineScope(coroutineContext)
  val recomposer = Recomposer(coroutineContext)
  composeView.compositionContext = recomposer
  // todo do i need to manually cancel this scope/job when service onDestroy???
  runRecomposeScope.launch {
    recomposer.runRecomposeAndApplyChanges()
  }
}

// https://stackoverflow.com/questions/64585547/jetpack-compose-crash-when-adding-view-to-window-manager
internal class MyLifecycleOwner : SavedStateRegistryOwner {
  private var mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
  private var mSavedStateRegistryController: SavedStateRegistryController =
    SavedStateRegistryController.create(this)
  val isInitialized: Boolean
    get() = true

  override fun getLifecycle(): Lifecycle {
    return mLifecycleRegistry
  }

  fun setCurrentState(state: Lifecycle.State) {
    mLifecycleRegistry.currentState = state
  }

  fun handleLifecycleEvent(event: Lifecycle.Event) {
    mLifecycleRegistry.handleLifecycleEvent(event)
  }

  override fun getSavedStateRegistry(): SavedStateRegistry {
    return mSavedStateRegistryController.savedStateRegistry
  }

  fun performRestore(savedState: Bundle?) {
    mSavedStateRegistryController.performRestore(savedState)
  }

  fun performSave(outBundle: Bundle) {
    mSavedStateRegistryController.performSave(outBundle)
  }
}