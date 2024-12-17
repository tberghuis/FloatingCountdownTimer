package xyz.tberghuis.floatingtimer

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

fun Context.openPlayStorePage() {
  val intent = Intent(Intent.ACTION_VIEW)
    .setData(Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
  try {
    startActivity(
      Intent(intent)
        .setPackage("com.android.vending")
    )
  } catch (exception: ActivityNotFoundException) {
    startActivity(intent)
  }
}

fun Context.openGithubIssues() {
  val intent = Intent(Intent.ACTION_VIEW)
    .setData(Uri.parse("https://github.com/tberghuis/FloatingCountdownTimer/issues"))
  startActivity(intent)
}

@Preview
@Composable
fun OpenPlayPage() {
  val context = LocalContext.current
  Column {
    Button(onClick = {
      context.openPlayStorePage()
    }) {
      Text("play store")
    }

    Button(onClick = {
      context.openGithubIssues()
    }) {
      Text("github issues")
    }
  }
}



