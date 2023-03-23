package xyz.tberghuis.floatingtimer.tmp.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd

class BlankFragment : Fragment() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    logd("BlankFragment onCreateView")

    return inflater.inflate(R.layout.fragment_blank, container, false)
  }
}