package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.R

@Composable
fun PremiumDialog(premiumVmc: PremiumVmc) {
  if (premiumVmc.showPurchaseDialog) {
    AlertDialog(
      onDismissRequest = {
        premiumVmc.showPurchaseDialog = false
      },
      confirmButton = {
        TextButton(onClick = {
        }) {
          Text(stringResource(R.string.buy).uppercase())
        }
      },
      modifier = Modifier,
      dismissButton = {
        TextButton(onClick = { premiumVmc.showPurchaseDialog = false }) {
          Text(stringResource(R.string.cancel).uppercase())
        }
      },
      title = { Text(stringResource(R.string.premium_feature)) },
      text = {
        Column {
          Text("Running more than 2 timers simultaneously requires premium.")
          Text("")
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Text("Floating Timer Premium")
            Text("\$X.XX")
          }
        }
      },
    )
  }
}