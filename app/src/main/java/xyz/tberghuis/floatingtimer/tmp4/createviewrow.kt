package xyz.tberghuis.floatingtimer.tmp4

import android.content.Context
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.logd

fun createViewRow(activity: ComponentActivity, params: WindowManager.LayoutParams): ComposeView {
  val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  return ComposeView(activity).apply {
    setContent {
      Box {
        Row(modifier = Modifier
          .runOnceOnGloballyPositioned {
            logd("runOnceOnGloballyPositioned ${it.size}")
            params.width = it.size.width.dpToPx(activity)
            params.height = it.size.height.dpToPx(activity)
            windowManager.updateViewLayout(this@apply, params)
          }
          .border(1.dp, Color.Black))
        {
          Text("this is label")
          Box(modifier = Modifier
            .size(60.dp)
            .background(Color.Green)) {
            Text("00:59")
          }
        }
      }
    }
    setViewTreeLifecycleOwner(activity)
    setViewTreeSavedStateRegistryOwner(activity)
  }
}