package xyz.tberghuis.floatingtimer.tmp.fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import xyz.tberghuis.floatingtimer.logd

class DemoActvity : ComponentActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("ComponentActivity onCreate")
  }
}