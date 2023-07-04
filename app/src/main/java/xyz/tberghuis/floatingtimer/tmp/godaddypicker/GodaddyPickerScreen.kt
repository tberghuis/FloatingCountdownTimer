package xyz.tberghuis.floatingtimer.tmp.godaddypicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.delay
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.LocalHaloColourState

@Composable
fun GodaddyPickerScreen(
  vm: GdpsVm = viewModel()
) {

  // todo calc screen width and height
  // test on small and large devices

  // it already deals with screen rotating


//  val haloColour = vm.haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
  val haloColourState = LocalHaloColourState.current



  LaunchedEffect(haloColourState.value) {
    logd("GodaddyPickerScreen haloColour ${haloColourState.value}")
  }


  Column {
    Text("godaddy screen")


    ClassicColorPicker(
      modifier = Modifier
        .height(300.dp)
        .widthIn(0.dp, 300.dp),
      color = HsvColor.from(haloColourState.value),
      onColorChanged = { color: HsvColor ->
        // Do something with the color
        logd("color: $color")


      }
    )


    Button(onClick = {
      vm.saveHaloColor(Color.Blue)
    }) {
      Text("set color blue")
    }


  }
}