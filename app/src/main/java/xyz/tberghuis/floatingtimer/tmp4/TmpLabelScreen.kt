package xyz.tberghuis.floatingtimer.tmp4

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

@Composable
fun TmpLabelScreen(
  vm: TmpLabelScreenVm = viewModel()
) {

  val context = LocalContext.current

  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("hello label screen")
    Button(onClick = { vm.createOverlay(context as ComponentActivity) }) {
      Text("create overlay")
    }


    Button(onClick = { vm.updateSize() }) {
      Text("update size")
    }


  }
}

@Composable
fun Modifier.runOnceOnGloballyPositioned(runOnce: (LayoutCoordinates) -> Unit): Modifier {
  var hasRun by remember { mutableStateOf(false) }
  return this then Modifier.onGloballyPositioned {
    if (!hasRun) {
      hasRun = true
      runOnce(it)
    }
  }
}
