package xyz.tberghuis.floatingtimer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import xyz.tberghuis.floatingtimer.service.minutesToAnnounce

class SpeakScheduleTest {
  @Test
  fun announcesEachMinuteWithOneMinuteInterval() {
    assertEquals(4, minutesToAnnounce(241, 240, 1))
    assertEquals(1, minutesToAnnounce(61, 60, 1))
  }

  @Test
  fun silentBetweenPoints() {
    assertNull(minutesToAnnounce(240, 239, 1))
    assertNull(minutesToAnnounce(100, 99, 1))
    assertNull(minutesToAnnounce(60, 59, 1))
  }

  @Test
  fun doesNotAnnounceStartingTime() {
    // 10:00 timer, first tick 600 -> 599 should be silent
    assertNull(minutesToAnnounce(600, 599, 1))
    assertNull(minutesToAnnounce(600, 599, 5))
  }

  @Test
  fun fiveAndTenMinuteIntervals() {
    assertEquals(5, minutesToAnnounce(301, 300, 5))
    assertNull(minutesToAnnounce(241, 240, 5))
    assertEquals(10, minutesToAnnounce(601, 600, 10))
    assertNull(minutesToAnnounce(301, 300, 10))
  }

  @Test
  fun handlesSkippedTick() {
    // tick rounding jumped from 301 straight to 299
    assertEquals(5, minutesToAnnounce(301, 299, 5))
  }

  @Test
  fun neverAnnouncesZeroOrWhenNotCountingDown() {
    assertNull(minutesToAnnounce(1, 0, 1))
    assertNull(minutesToAnnounce(60, 60, 1))
    assertNull(minutesToAnnounce(59, 61, 1))
    assertNull(minutesToAnnounce(61, 60, 0))
  }
}
