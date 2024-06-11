package xyz.tberghuis.floatingtimer.tmp6

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme


@Composable
fun TmpColumnScroll() {
  LazyColumn(
    modifier = Modifier.background(Color.Cyan),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(100) { index ->
      Box(
        modifier = Modifier
          .background(Color.LightGray)
          .widthIn(0.dp, 232.dp),
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text("start line $index")
          Text("end line $index")
        }
      }
    }
  }

}

@Preview
@Composable
fun TmpColumnScrollPreview() {
  FloatingTimerTheme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    ) {
      TmpColumnScroll()
    }
  }
}