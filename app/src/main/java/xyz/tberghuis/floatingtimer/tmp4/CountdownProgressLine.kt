package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun CountdownProgressLine(timeLeftFraction: Float, arcWidth: Dp, haloColor: Color) {

  Text("timeLeftFraction $timeLeftFraction arcWidth $arcWidth haloColor $haloColor")
}