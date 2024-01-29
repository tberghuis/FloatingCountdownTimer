package xyz.tberghuis.floatingtimer

import androidx.test.core.app.takeScreenshot
import androidx.test.core.graphics.writeToTestStorage
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.screenshot.captureToBitmap
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ScreenshotTest {

  @get:Rule
  var nameRule = TestName()

  @get:Rule
  val activityScenarioRule = activityScenarioRule<MainActivity>()


//  @Test
//  @Throws(IOException::class)
//  fun saveActivityBitmap() {
////    onView(isRoot())
////      .captureToBitmap()
////      .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}")
//  }


  @Test
  @Throws(IOException::class)
  fun saveDeviceScreenBitmap() {
    takeScreenshot()
      .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}")
  }
}