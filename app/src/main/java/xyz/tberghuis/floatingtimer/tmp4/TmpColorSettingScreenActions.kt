package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.viewmodels.ColorSettingViewModel

@Composable
fun TmpColorSettingScreenActions(
  vm: ColorSettingViewModel = viewModel()
) {

  val nav = LocalNavController.current

  // this is bad, but what am i going to do????
  // i need to use a proper nav lib???
  // doitwrong as it works for now
  val tmpHaloColorOwner: TmpHaloColorOwner? = when (vm.timerType) {
    "stopwatch" -> {
      // todo
      null
    }

    "countdown" -> {
      viewModel<TmpCountdownScreenVm>(nav.previousBackStackEntry!!)
    }

    else -> {
      null
    }
  }


  val ifPremiumCallback = when (vm.timerType) {
    "stopwatch" -> {
      {
        // todo
//        homeVm.stopwatchHaloColor = vm.settingsTimerPreviewVmc.haloColor
        nav.popBackStack()
      }
    }

    "countdown" -> {
      {
        tmpHaloColorOwner?.haloColor = vm.settingsTimerPreviewVmc.haloColor
        nav.popBackStack()
      }
    }

    else -> {
      {
        vm.saveDefaultHaloColor()
        // todo, just collect default in each screen VM
//        homeVm.countdownHaloColor = vm.settingsTimerPreviewVmc.haloColor
//        homeVm.stopwatchHaloColor = vm.settingsTimerPreviewVmc.haloColor
        nav.popBackStack()
      }
    }
  }

  Row(
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Button(onClick = {
      nav.popBackStack()
    }) {
      Text(stringResource(R.string.cancel).uppercase())
    }
    Button(onClick = {
      vm.okButtonClick {
        ifPremiumCallback()
      }
    }) {
      Text(stringResource(R.string.save).uppercase())
    }
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_halo_colour)) {
    ifPremiumCallback()
  }
}