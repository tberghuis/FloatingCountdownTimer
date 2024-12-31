package xyz.tberghuis.floatingtimer.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.openGithubIssues
import xyz.tberghuis.floatingtimer.openPlayStorePage
import xyz.tberghuis.floatingtimer.viewmodels.SettingsScreenVm
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
  val navController = LocalNavController.current
  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.settings)) },
        navigationIcon = {
          IconButton(onClick = {
            navController.navigateUp()
          }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    SettingsScreenContent(padding)
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreenContent(
  padding: PaddingValues,
  vm: SettingsScreenVm = viewModel(),
  sharedVm: SharedVm = viewModel(LocalContext.current as ComponentActivity)
) {
  val navController = LocalNavController.current
  val looping by vm.loopingFlow.collectAsState(true)
  val haloColour by vm.haloColourFlow.collectAsState(DEFAULT_HALO_COLOR)
  val purchased by vm.haloColourPurchasedFlow.collectAsState(null)
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .padding(padding)
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    ListItem(
      headlineContent = { Text(stringResource(R.string.premium_upgrade)) },
      modifier = Modifier.clickable {
        navController.navigate("premium")
      },
      supportingContent = {
        val t = when (purchased) {
          true -> stringResource(R.string.unlocked)
          false -> stringResource(R.string.locked)
          null -> ""
        }
        Text(t)
      },
    )

    HorizontalDivider()
    Text(
      stringResource(R.string.timer),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp),
      color = MaterialTheme.colorScheme.primary,
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.change_default_color)) },
      modifier = Modifier
        .semantics { testTagsAsResourceId = true }
        .testTag("change_color")
        .clickable {
          navController.navigate("change_color")
        },
      trailingContent = {
        Icon(
          Icons.Filled.Circle,
          contentDescription = "color",
          modifier = Modifier.size(35.dp),
          tint = haloColour
        )
      },
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.change_size)) },
      modifier = Modifier
        .semantics { testTagsAsResourceId = true }
        .testTag("change_size")
        .clickable {
          navController.navigate("change_size")
        })

    HorizontalDivider()
    Text(
      stringResource(R.string.countdown),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp),
      color = MaterialTheme.colorScheme.primary,
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.ringtone)) },
      modifier = Modifier.clickable {
        navController.navigate("countdown_ringtone")
      },
      supportingContent = { Text(vm.currentRingtoneVmc.currentRingtoneTitle) },
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.repeating_alarm_sound_vibration)) },
      supportingContent = { Text(stringResource(R.string.off_ringtone_will_only_play_once)) },
      trailingContent = {
        Switch(
          checked = looping,
          onCheckedChange = {
            logd("onCheckedChange $it")
            vm.updateLooping(it)
          },
        )
      },
    )

    HorizontalDivider()
    Text(
      stringResource(R.string.actions),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp),
      color = MaterialTheme.colorScheme.primary,
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.cancel_all_timers)) },
      modifier = Modifier.clickable {
        sharedVm.cancelAllTimers()
      })
    ListItem(
      headlineContent = { Text(stringResource(R.string.save_timer_positions)) },
      modifier = Modifier.clickable {
        sharedVm.saveTimerPositions()
      })




    HorizontalDivider()
    Text(
      stringResource(R.string.links),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp),
      color = MaterialTheme.colorScheme.primary,
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.play_store)) },
      modifier = Modifier.clickable {
        context.openPlayStorePage()
      },
      supportingContent = { Text(stringResource(R.string.leave_a_review)) },
      trailingContent = {
        Icon(
          Icons.Default.OpenInBrowser,
          contentDescription = "open",
        )
      },
    )
    ListItem(
      headlineContent = { Text(stringResource(R.string.github_issues)) },
      modifier = Modifier.clickable {
        context.openGithubIssues()
      },
      supportingContent = { Text(stringResource(R.string.report_a_bug_or_request_a_feature)) },
      trailingContent = {
        Icon(
          Icons.Default.OpenInBrowser,
          contentDescription = "open",
        )
      }
    )
  }
}