package xyz.tberghuis.floatingtimer.tmp.fragment

import android.content.Context
import android.graphics.PixelFormat
import android.view.LayoutInflater
import android.view.WindowManager
import xyz.tberghuis.floatingtimer.R

class FragmentController(val service: FragmentService) {


  fun addView() {
    val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

//    val fragment = BlankFragment()

    val params = WindowManager.LayoutParams(
      500,
      500,
      0,
      0,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    )

// https://levelup.gitconnected.com/add-view-outside-activity-through-windowmanager-1a70590bad40
    val floatView = (service.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
      R.layout.fragment_blank,
      null
    )

    windowManager.addView(floatView, params)

  }

}