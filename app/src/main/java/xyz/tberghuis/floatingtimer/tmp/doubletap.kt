package xyz.tberghuis.floatingtimer.tmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme


class DoubleTapActivity : ComponentActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      FloatingTimerTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        ) {
          MaccasScreen()
        }
      }
    }
  }
}

@Composable
fun DoubleTapDemo() {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  )
  {
    Box(
      modifier = Modifier

        .pointerInput(Unit) {
          detectTapGestures(
            onDoubleTap = {
              logd("onDoubleTap")
            },
            onTap = {
              logd("onTap")
            }
          )
        }

        .clickable {
          logd("clickable")
        }


        .border(1.dp, Color.Black),
    ) {
      Text("double tap")

    }
  }
}
