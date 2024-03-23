package xyz.tberghuis.floatingtimer.tmp5

import android.content.Context
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.LocalTimerViewHolder
import xyz.tberghuis.floatingtimer.tmp4.runOnceOnGloballyPositioned

//@Composable
//fun TmpTimerLabelView() {
//  val tvh = LocalTimerViewHolder.current
//  val windowManager = tvh.service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//
//  Box {
//    Row(modifier = Modifier
//      .runOnceOnGloballyPositioned {
//        logd("runOnceOnGloballyPositioned ${it.size}")
//        tvh.params.width = it.size.width
//        tvh.params.height = it.size.height
//        windowManager.updateViewLayout(tvh.view, tvh.params)
//      }
//      .border(1.dp, Color.Black))
//    {
//      Text("this is label")
//      Box(
//        modifier = Modifier
//          .size(60.dp)
//          .background(Color.Green)
//      ) {
//        Text("00:59")
//      }
//    }
//  }
//}

@Composable
fun TmpTimerLabelView() {
  Box {
    Row(modifier = Modifier
      .runOnceOnGloballyPositioned {
        // see commented code above
      }
      .border(1.dp, Color.Black))
    {
      Text("this is label")
      Box(
        modifier = Modifier
          .size(60.dp)
          .background(Color.Green)
      ) {
        Text("00:59")
      }
    }
  }
}