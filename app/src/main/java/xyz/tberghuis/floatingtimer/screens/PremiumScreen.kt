package xyz.tberghuis.floatingtimer.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumScreenContentThanks
import xyz.tberghuis.floatingtimer.viewmodels.PremiumScreenVm

@Composable
fun PremiumScreen(
  vm: PremiumScreenVm = viewModel()
) {
  Scaffold(
    modifier = Modifier,
    topBar = { PremiumTopAppBar() },
    snackbarHost = {
      SnackbarHost(vm.snackbarHostState)
    },
  ) {
    Box(
      modifier = Modifier
        .padding(it)
        .fillMaxSize(),
      contentAlignment = Alignment.TopCenter,
    ) {
      PremiumScreenContent()
    }
  }
}

@Composable
fun PremiumScreenContent(
  vm: PremiumScreenVm = viewModel()
) {
  val isPremiumPurchased by vm.premiumPurchasedStateFlow.collectAsState()

  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .width(IntrinsicSize.Max)
      .padding(10.dp),
  ) {
    when (isPremiumPurchased) {
      true -> PremiumScreenContentThanks()
      false -> PremiumScreenContentNotPurchased()
      null -> {
        return
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumTopAppBar() {
  val navController = LocalNavController.current
  TopAppBar(
    title = { Text(stringResource(R.string.premium)) },
    modifier = Modifier,
    navigationIcon = {
      IconButton(onClick = {
        navController.navigateUp()
      }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
      }
    },
  )
}


@Composable
fun PremiumScreenContentNotPurchased(vm: PremiumScreenVm = viewModel()) {
  val context = LocalContext.current
  val focusManager = LocalFocusManager.current
  val navController = LocalNavController.current
  var priceTextStyle = LocalTextStyle.current
  if (vm.validDiscountCode) {
    priceTextStyle = priceTextStyle.copy(textDecoration = TextDecoration.LineThrough)
  }
  val discountCodeSupportingText: @Composable (() -> Unit)? = if (vm.discountCodeError) {
    { Text(stringResource(R.string.invalid)) }
  } else null

  Text(
    "${stringResource(R.string.premium_screen_text1)}\n${stringResource(R.string.premium_screen_features)}",
    modifier = Modifier.widthIn(max = 350.dp),
  )

  ElevatedCard(
    modifier = Modifier.padding(top = 20.dp)
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
//      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(stringResource(R.string.upgrade_to_premium))
        Text(
          vm.premiumPriceText,
          style = priceTextStyle
        )
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
      ) {
        if (vm.validDiscountCode) {
          Text(vm.discountPriceText)
        } else {
          Text("")
        }
      }

      Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        TextField(
          value = vm.discountCode,
          onValueChange = {
            vm.discountCodeError = false
            vm.discountCode = it
          },
          modifier = Modifier.width(180.dp),
          label = {
            Text(stringResource(R.string.discount_code))
          },
          supportingText = discountCodeSupportingText,
          isError = vm.discountCodeError,
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
          keyboardActions = KeyboardActions(
            onDone = {
              vm.applyDiscountCode(focusManager)
            }
          ),
        )
        Button(onClick = {
          vm.applyDiscountCode(focusManager)
        }) {
          Text(stringResource(R.string.apply))
        }
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
      ) {
        Button(onClick = {
          navController.popBackStack()
        }) {
          Text(stringResource(R.string.cancel))
        }
        Button(onClick = {
          vm.buy(context as Activity)
        }) {
          Text(stringResource(R.string.buy))
        }
      }
    }
  }
}