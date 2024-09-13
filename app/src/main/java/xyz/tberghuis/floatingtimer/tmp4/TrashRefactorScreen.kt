package xyz.tberghuis.floatingtimer.tmp4

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TrashRefactorScreen(
  vm: Tmp4Vm = viewModel()
) {
  Column(
    modifier = Modifier.onPreviewKeyEvent {
      logd("onPreviewKeyEvent $it")
      if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
        when (it.nativeKeyEvent.keyCode) {
          KeyEvent.KEYCODE_1 -> {
            logd("number 1 pressed")
          }
        }
      }
      true
    },
  ) {
    Text("TrashRefactorScreen")
    Button(onClick = {
      logd("${vm.willitblend}")
    }) {
      Text("button")
    }
  }
}

@Preview
@Composable
fun TrashRefactorScreenPreview() {
  TrashRefactorScreen()
}