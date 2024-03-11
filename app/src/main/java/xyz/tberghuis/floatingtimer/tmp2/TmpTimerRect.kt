package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.TmpVm
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpTimerRect(
  vm: TmpVm = viewModel()
) {
  // represents window.addView size....

  val density = LocalDensity.current

  Box(
    modifier = Modifier
      .width(vm.settingsTimerPreviewVmc.widthDp)
      .height(vm.settingsTimerPreviewVmc.heightDp)
      .padding(5.dp)
      .onGloballyPositioned { layoutCoords ->
        logd("onGloballyPositioned layoutCoords.size ${layoutCoords.size}")
        val dpValue = with(density) { layoutCoords.size.height.toDp() }

        logd("height dpValue $dpValue")
      },
    contentAlignment = Alignment.Center,
  ) {

    Surface(
      modifier = Modifier,
      shape = RoundedCornerShape(10.dp),
      shadowElevation = 5.dp,
    ) {
      Column(
        modifier = Modifier.background(Color.White)
          .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
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