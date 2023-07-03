package xyz.tberghuis.floatingtimer.tmp.colorpicker

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import xyz.tberghuis.floatingtimer.LocalHaloColour

@Composable
fun ColorPickerScreen(
  vm: ColorPickerScreenVm = viewModel()
) {
  val controller = rememberColorPickerController()

//  val controller = remember {
//    ColorPickerController().apply {
//      this.selectByCoordinate()
//    }
//  }


  val haloColour = LocalHaloColour.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(all = 30.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      AlphaTile(
        modifier = Modifier
          .fillMaxWidth()
          .height(60.dp)
          .clip(RoundedCornerShape(6.dp)),
        controller = controller
      )
    }
    HsvColorPicker(
      modifier = Modifier
        .fillMaxWidth()
        .height(450.dp)
        .padding(10.dp),
      controller = controller,
      onColorChanged = {
        Log.d("Color", it.hexCode)
      },
      initialColor = haloColour
    )
    AlphaSlider(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(35.dp),
      controller = controller,
      tileOddColor = Color.White,
      tileEvenColor = Color.Black
    )
    BrightnessSlider(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(35.dp),
      controller = controller,
      initialColor = haloColour
    )
  }

}