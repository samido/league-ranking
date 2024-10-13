package com.league.ranking

/**
 * @author Sam Rabophala
 * @version 1.0
 * @since 2024-10-12
 * A case class representing a team and its points.
 *
 * @param name The name of the team.
 * @param points The points accumulated by the team.
 */
case class Team(name: String, var points: Int = 0) {
  def addPoints(additionalPoints: Int): Unit = {
    points += additionalPoints
  }
}
