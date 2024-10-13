package com.league.ranking

import scala.collection.mutable

/**
 * @author Sam Rabophala
 * @version 1.0
 * @since 2024-10-12
 * A class representing the league, containing the teams and their results.
 */
class League {
  private val teams: mutable.Map[String, Team] = mutable.Map()

  /**
   * Adds a match result to the league.
   *
   * @param homeTeamName The name of the home team.
   * @param homeScore The score of the home team.
   * @param awayTeamName The name of the away team.
   * @param awayScore The score of the away team.
   */
  def addMatchResult(homeTeamName: String, homeScore: Int, awayTeamName: String, awayScore: Int): Unit = {
    // Ensure both teams are registered
    val homeTeam = teams.getOrElseUpdate(homeTeamName, Team(homeTeamName))
    val awayTeam = teams.getOrElseUpdate(awayTeamName, Team(awayTeamName))

    // Update points based on the match result
    if (homeScore > awayScore) {
      homeTeam.addPoints(3) // Home win
    } else if (homeScore < awayScore) {
      awayTeam.addPoints(3) // Away win
    } else {
      homeTeam.addPoints(1) // Draw
      awayTeam.addPoints(1) // Draw
    }
  }

  /**
   * Gets the current standings of the league.
   *
   * @return A list of teams sorted by points.
   */
  def getStandings: List[Team] = {
    teams.values.toList.sortBy(-_.points)
  }
}
