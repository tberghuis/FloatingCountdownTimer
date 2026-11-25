package xyz.tberghuis.floatingtimer.service

/**
 * Works out if a countdown tick has just passed a spoken announcement point.
 *
 * Announcement points are every [intervalMinutes] minutes of remaining time
 * (for example 15:00, 10:00, 5:00 with a 5 minute interval).
 * Ticks can sometimes skip a second, so this checks if a point was passed
 * between [previousSeconds] and [currentSeconds], not only an exact match.
 *
 * @return the whole minutes remaining to announce, or null if nothing to say.
 */
fun minutesToAnnounce(previousSeconds: Int, currentSeconds: Int, intervalMinutes: Int): Int? {
  if (intervalMinutes <= 0) return null
  if (currentSeconds <= 0 || currentSeconds >= previousSeconds) return null
  val intervalSeconds = intervalMinutes * 60
  // largest announcement point at or below previousSeconds - 1
  val point = ((previousSeconds - 1) / intervalSeconds) * intervalSeconds
  if (point <= 0 || point < currentSeconds) return null
  return point / 60
}

val SPEAK_INTERVAL_OPTIONS = listOf(1, 5, 10)
