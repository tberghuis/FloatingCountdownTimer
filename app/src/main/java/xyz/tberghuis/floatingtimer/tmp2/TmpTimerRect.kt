package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.tmp.TmpVm
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpTimerRect(
  vm: TmpVm = viewModel()
) {
  // represents window.addView size....


  Box(
    modifier = Modifier
      .width(vm.settingsTimerPreviewVmc.bubbleSizeDp)
      .padding(5.dp),
    contentAlignment = Alignment.Center,
  ) {

    Surface(
      modifier = Modifier,
      shape = RoundedCornerShape(10.dp),
      shadowElevation = 5.dp,
    ) {
      Column(
        modifier = Modifier,
//          .width(vm.settingsTimerPreviewVmc.bubbleSizeDp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
//        Text("00:59")
        TimeDisplay(59, vm.settingsTimerPreviewVmc.fontSize)

        Box(
          modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
          ),
          contentAlignment = Alignment.TopStart,
        ) {
          CountdownProgressLine(
            0.5f,
            vm.settingsTimerPreviewVmc.arcWidth,
            vm.settingsTimerPreviewVmc.haloColor
          )
        }


      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PaddingSizeDemo() {

  Column(modifier = Modifier.size(100.dp)) {
    Box(
      Modifier
        .padding(5.dp)
        .width(50.dp)
        .background(Color.Green)
    ) {
      Text("hello")
    }

    Box(
      Modifier
        .width(50.dp)
        .padding(5.dp)

        .background(Color.Green)
    ) {
      Text("hello")
    }



    Box(Modifier) {
      Text("hello")
    }
  }


}

