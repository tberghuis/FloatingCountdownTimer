package xyz.tberghuis.floatingtimer.composables

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.PremiumVmc

@Composable
fun PremiumDialog(premiumVmc: PremiumVmc, reason: String, purchasedCallback: () -> Unit = {}) {
  val context = LocalContext.current

  if (premiumVmc.showPurchaseDialog) {
    // hack
    if (premiumVmc.premiumPriceText == "") {
      LaunchedEffect(Unit) {
        premiumVmc.updateHaloColorChangePriceText()
      }
    }

    AlertDialog(
      onDismissRequest = {
        premiumVmc.showPurchaseDialog = false
      },
      confirmButton = {
        TextButton(onClick = {
          premiumVmc.buy(context as Activity) {
            purchasedCallback()
          }
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
          Text(reason)
          Text("")
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Text(stringResource(R.string.ft_premium))
            Text(premiumVmc.premiumPriceText)
          }
        }
      },
    )
  }
}