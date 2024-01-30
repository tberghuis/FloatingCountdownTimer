package xyz.tberghuis.floatingtimer


import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @Rule
  @JvmField
  var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

  @Rule
  @JvmField
  var mGrantPermissionRule =
    GrantPermissionRule.grant(
      "android.permission.POST_NOTIFICATIONS"
    )

  @Test
  fun mainActivityTest() {
  }
}
