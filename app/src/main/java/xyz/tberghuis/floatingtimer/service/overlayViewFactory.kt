package xyz.tberghuis.floatingtimer.service

import android.app.Service
import android.os.Bundle
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//// https://gist.github.com/handstandsam/6ecff2f39da72c0b38c07aa80bbb5a2f
//fun overlayViewFactory(service: Service): ComposeView {
//  // is service correct context
//  val view = ComposeView(service)
//  view.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//  val lifecycleOwner = MyLifecycleOwner()
//  lifecycleOwner.performRestore(null)
//  lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
//  view.setViewTreeLifecycleOwner(lifecycleOwner)
//  view.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
//  val viewModelStore = ViewModelStore()
//  val viewModelStoreOwner = object : ViewModelStoreOwner {
//    override val viewModelStore: ViewModelStore
//      get() = viewModelStore
//  }
//  view.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
//  // https://stackoverflow.com/questions/74433762/how-to-show-a-view-when-an-android-app-is-not-active-focused
//  val coroutineContext = AndroidUiDispatcher.CurrentThread
//  val runRecomposeScope = CoroutineScope(coroutineContext)
//  val recomposer = Recomposer(coroutineContext)
//  view.compositionContext = recomposer
//  // todo do i need to manually cancel this scope/job when service onDestroy???
//  runRecomposeScope.launch {
//    recomposer.runRecomposeAndApplyChanges()
//  }
//  return view
//}
//
//// https://stackoverflow.com/questions/64585547/jetpack-compose-crash-when-adding-view-to-window-manager
//private class MyLifecycleOwner : SavedStateRegistryOwner {
//  private var mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
//  private var mSavedStateRegistryController: SavedStateRegistryController =
//    SavedStateRegistryController.create(this)
//  val isInitialized: Boolean
//    get() = true
//  override val lifecycle: Lifecycle
//    get() = mLifecycleRegistry
//  override val savedStateRegistry: SavedStateRegistry
//    get() = mSavedStateRegistryController.savedStateRegistry
//  fun setCurrentState(state: Lifecycle.State) {
//    mLifecycleRegistry.currentState = state
//  }
//  fun handleLifecycleEvent(event: Lifecycle.Event) {
//    mLifecycleRegistry.handleLifecycleEvent(event)
//  }
//  fun performRestore(savedState: Bundle?) {
//    mSavedStateRegistryController.performRestore(savedState)
//  }
//  fun performSave(outBundle: Bundle) {
//    mSavedStateRegistryController.performSave(outBundle)
//  }
//}