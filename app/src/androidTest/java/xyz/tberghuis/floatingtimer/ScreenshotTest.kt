package xyz.tberghuis.floatingtimer

import android.graphics.Bitmap
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.takeScreenshot
import androidx.test.core.graphics.writeToTestStorage
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import java.io.FileOutputStream
import java.io.IOException

class ScreenshotTest {

  @get:Rule
  var nameRule = TestName()

  @get:Rule
  val composeTestRule = createAndroidComposeRule<MainActivity>()


  @Test
  @Throws(IOException::class)
  fun saveDeviceScreenBitmap() {
    takeScreenshot()
      .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}")
  }


  @Test
  @Throws(IOException::class)
  fun willitblend() {
    composeTestRule.apply {
//      val bitmap = onRoot().captureToImage().asAndroidBitmap()
//      saveScreenshot("willitblend", bitmap)

      takeScreenshot()
        .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}")
    }
  }


//  @Test
//  @Throws(IOException::class)
//  fun runTestDemo() = runTest {
//    delay(5000)
//    composeTestRule.apply {
//      this.onRoot().captureToImage()
//    }
//    delay(5000)
//  }
}

private fun saveScreenshot(filename: String, bmp: Bitmap) {
  val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath
  FileOutputStream("$path/$filename.png").use { out ->
    bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
  }
  println("Saved screenshot to $path/$filename.png")
}
