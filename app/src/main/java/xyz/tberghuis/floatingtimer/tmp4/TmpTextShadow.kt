package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun TmpTextShadow() {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Box {
      Box(
        Modifier
          .background(Color.Green)
          .size(200.dp)
      )
      Column {
        OutlinedTextWithShadow("00:59")
        OutlinedTextWithShadow("00:59")
      }

    }
  }
}

@Composable
fun TextShadow() {
  Text(
    text = "Sample",
    style = TextStyle.Default.copy(
      fontSize = 64.sp,
      drawStyle = Stroke(
        miter = 10f,
        width = 5f,
        join = StrokeJoin.Round
      ),

      shadow = Shadow(
        color = Color.Gray,
        offset = Offset(-5f, 5f),
        blurRadius = 8f
      )
    )
  )
}

@Composable
fun TextShadowWhite() {
  Text(
    text = "Sample",
    style = TextStyle.Default.copy(
      color = Color.White,
      fontSize = 64.sp,
      drawStyle = Fill
    )
  )
}


// text min and max font sizes

@Composable
fun OutlinedTextWithShadow(
  text: String,
  fontSize: TextUnit = 64.sp,
) {
  Box {
    Text(
      text = text,
      style = TextStyle.Default.copy(
        fontSize = fontSize,
        drawStyle = Stroke(
          miter = 10f,
          width = 5f,
          join = StrokeJoin.Round
        ),
        shadow = Shadow(
          color = Color.Gray,
          offset = Offset(-5f, 5f),
          blurRadius = 8f
        )
      )
    )

    Text(
      text = text,
      style = TextStyle.Default.copy(
        color = Color.White,
        fontSize = fontSize,
        drawStyle = Fill
      )
    )
  }
}

