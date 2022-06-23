package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FabDemo() {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = {}) {
        Text("X")
      }
    },
    content = { FabDemoContent() }
  )
}

@Composable
fun FabDemoContent() {
  Column(modifier = Modifier.padding(20.dp)) {
    Text("hello fab")
    Surface(
      shape = RoundedCornerShape(percent = 50),
      elevation = 5.dp

    ) {

      Box(
        modifier = Modifier
          .size(100.dp)
//          .background(Color.Blue),

        ) {}
    }
    Spacer(modifier = Modifier.height(30.dp))
    Card(
      elevation = 4.dp,
    ) {
      Column(modifier = Modifier.padding(10.dp)) {
        Text("AB CDE", fontWeight = FontWeight.W700)
        Text("+0 12345678")
        Text("XYZ city.", color = Color.Gray)
      }
    }

  }
}




/*
*  Surface(
      modifier = Modifier
        .offset {
          timerOffset
        }
//        .background(Color.Red)
        .size(TIMER_SIZE_DP.dp)
        .padding(PROGRESS_ARC_WIDTH / 2)
        .zIndex(1f),
      shape = RoundedCornerShape(percent = 50),
      elevation = 10.dp
    ) {
      Box(
        contentAlignment = Alignment.Center
      ) {
        ProgressArc(timeLeftFraction)
        TimerDisplay()
      }
    }
*
*
* */
