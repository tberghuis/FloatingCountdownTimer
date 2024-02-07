package xyz.tberghuis.floatingtimer.tmp3

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpSettingsScreen(nav: NavHostController) {


  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Settings") },
        navigationIcon = {
          IconButton(onClick = {
          }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        modifier = Modifier,
      )
    },
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
    ) {
      DefaultColor(nav)
      DefaultSize(nav)

      Text("notification settings")
      Text("draw overlay setting")

      Text("battery settings")


      Text("dontkillmyapp.com")
      Text("support (github new issue)")
      Text("app version")
    }
  }
}

@Composable
fun DefaultColor(nav: NavHostController) {
  Text(
    "Default Color",
    modifier = Modifier.clickable {
      nav.navigate("change_color/default")
    },
  )
}

@Composable
fun DefaultSize(nav: NavHostController) {
  Text(
    "Default Size",
    modifier = Modifier.clickable {
      nav.navigate("change_size/default")
    },
  )
}


