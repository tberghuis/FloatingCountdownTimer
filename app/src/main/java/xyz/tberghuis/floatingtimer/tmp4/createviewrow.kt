package xyz.tberghuis.floatingtimer.tmp4

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.tmp5.TmpTimerLabelView

fun createViewRow(activity: ComponentActivity): ComposeView {
  return ComposeView(activity).apply {
    setContent {
      TmpTimerLabelView()
    }
    setViewTreeLifecycleOwner(activity)
    setViewTreeSavedStateRegistryOwner(activity)
  }
}