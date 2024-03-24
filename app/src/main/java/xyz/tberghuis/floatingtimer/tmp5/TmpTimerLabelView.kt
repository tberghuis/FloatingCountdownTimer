package xyz.tberghuis.floatingtimer.tmp5

import android.content.Context
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.composables.CountdownProgressLine
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

// hardcoded from scratch
// TimerRectView as reference

@Preview(showBackground = true)
@Composable
fun TmpTimerLabelView(
//  isPaused: Boolean,
//  widthDp: Dp,
//  heightDp: Dp,
//  arcWidth: Dp,
//  haloColor: Color,
//  timeElapsed: Int,
//  timeLeftFraction: Float,
//  fontSize: TextUnit,
) {


  val isPaused = false
  val widthDp = Dp.Unspecified
  val heightDp = Dp.Unspecified
  val arcWidth = ARC_WIDTH_NO_SCALE
  val haloColor = Color.Green
  val timeElapsed = 59
  val timeLeftFraction = 1f
  val fontSize = TIMER_FONT_SIZE_NO_SCALE
  val label = "label"


  Box(
    modifier = Modifier
  ) {
    Box(
      modifier = Modifier
        .runOnceOnGloballyPositioned {
          // todo
        },
    ) {
      Column(
        modifier = Modifier
          .width(IntrinsicSize.Max)
      ) {
        Row(
          modifier = Modifier,
        ) {

          CompositionLocalProvider(
            LocalDensity provides Density(LocalDensity.current.density, 1f)
          ) {
            Text(
              label,
              fontSize = fontSize,
              fontFamily = FontFamily.Default,
              style = LocalTextStyle.current.copy(
                color = Color.Black,
              ),
            )
          }

          TimeDisplay(timeElapsed, fontSize)
        }
        CountdownProgressLine(
          timeLeftFraction,
          arcWidth,
          haloColor
        )
      }
    }
  }
}






