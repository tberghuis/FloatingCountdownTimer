package xyz.tberghuis.floatingtimer.tmp.godaddypicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import xyz.tberghuis.floatingtimer.logd

@Composable
fun GodaddyPickerScreen(
  vm: GdpsVm = viewModel()
) {

  // todo calc screen width and height
  // test on small and large devices

  // it already deals with screen rotating

  Column {
    Text("godaddy screen ${vm.dsfds}")


    ClassicColorPicker(
      modifier = Modifier
        .height(300.dp)
        .widthIn(0.dp, 300.dp),
      color = HsvColor.from(Color.Red),
      onColorChanged = { color: HsvColor ->
        // Do something with the color
        logd("color: $color")


      }
    )

  }
}