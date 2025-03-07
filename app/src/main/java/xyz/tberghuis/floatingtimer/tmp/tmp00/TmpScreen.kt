package xyz.tberghuis.floatingtimer.tmp.tmp00

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

@Composable
fun TmpScreen(
//  vm: TmpVm = viewModel()
) {
  Column(
    modifier = Modifier
      .padding(horizontal = 30.dp)
      .onPreviewKeyEvent {
        logd("onPreviewKeyEvent $it")
        if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
          when (it.nativeKeyEvent.keyCode) {
            KeyEvent.KEYCODE_1 -> {
              logd("1 getService")
//              vm.getService()
            }
          }
        }
        true
      },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("hello tmp screen")
//    TmpTextFieldLabel()
  }
}

@Preview
@Composable
fun TmpScreenPreview() {
  FloatingTimerTheme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    ) {
      TmpScreen()
    }
  }
}