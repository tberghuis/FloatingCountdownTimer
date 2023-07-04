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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.LocalHaloColour

@Composable
fun GodaddyPickerScreen(
  vm: GdpsVm = viewModel()
) {

  // todo calc screen width and height
  // test on small and large devices

  // it already deals with screen rotating


//  val haloColour = vm.haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)
  val haloColour = LocalHaloColour.current

  val colorFlow: MutableSharedFlow<HsvColor> = remember {
    MutableSharedFlow()
  }
  val scope = rememberCoroutineScope()
//
//  LaunchedEffect(haloColour) {
//    logd("GodaddyPickerScreen haloColour ${haloColour}")
//    colorFlow.emit(HsvColor.from(haloColour))
//  }

//  val colorFlow = remember {
//    vm.haloColourFlow.map { HsvColor.from(it) }
//  }


  Column {
    Text("godaddy screen")


    ClassicColorPicker(
      modifier = Modifier
        .height(300.dp)
        .widthIn(0.dp, 300.dp),
      color = HsvColor.from(haloColour),
      updateColorFlow = colorFlow,
      onColorChanged = { color: HsvColor ->
        // Do something with the color
        logd("color: $color")
        vm.colorPickerColor = color
      }
    )


    Button(onClick = {
      scope.launch {
        colorFlow.emit(HsvColor.from(Color.Blue))
      }
    }) {
      Text("set color blue")
    }

    Button(onClick = {
      vm.saveHaloColor()
    }) {
      Text("update user prefs")
    }


  }
}