package xyz.tberghuis.floatingtimer.composables

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.PremiumVmc

@Composable
fun PremiumDialog(premiumVmc: PremiumVmc, reason: String, purchasedCallback: () -> Unit = {}) {
  val context = LocalContext.current
  val navController = LocalNavController.current

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
          Text(
            text = stringResource(R.string.more_info),
            modifier = Modifier
              .padding(bottom = 15.dp)
              .clickable {
                premiumVmc.showPurchaseDialog = false
                navController.navigate("premium")
              },
            color = MaterialTheme.colorScheme.primary,
            style = LocalTextStyle.current.copy(textDecoration = TextDecoration.Underline)
          )

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