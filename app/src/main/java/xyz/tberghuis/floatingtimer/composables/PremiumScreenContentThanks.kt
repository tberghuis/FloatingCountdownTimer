package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@Composable
fun PremiumScreenContentThanks() {
  val navController = LocalNavController.current
  Text(
    "${stringResource(R.string.premium_screen_text2)}\n${stringResource(R.string.premium_screen_features)}",
    modifier = Modifier.widthIn(max = 350.dp),
  )
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp),
    horizontalArrangement = Arrangement.Center,
  ) {
    Button(onClick = {
      navController.popBackStack()
    }) {
      Text(stringResource(R.string.back))
    }
  }
}