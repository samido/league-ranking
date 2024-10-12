package com.league.ranking

import scala.collection.mutable

/**
 * A service to calculate the league rankings.
 */
class RankingService {

  /**
   * Calculates the ranking based on match results.
   *
   * @param results A list of match result strings.
   * @return A formatted ranking table.
   */
  def calculateRanking(results: List[String]): List[String] = {
    val teams = mutable.Map[String, Team]()

    results.foreach { result =>
      val Array(homeResult, awayResult) = result.split(", ")
      val Array(homeTeam, homeScore) = homeResult.split(" ")
      val Array(awayTeam, awayScore) = awayResult.split(" ")

      // Update home team points
      teams.getOrElseUpdate(homeTeam, Team(homeTeam))
      teams.getOrElseUpdate(awayTeam, Team(awayTeam))

      if (homeScore.toInt > awayScore.toInt) {
        teams(homeTeam).addPoints(3) // Home win
      } else if (homeScore.toInt < awayScore.toInt) {
        teams(awayTeam).addPoints(3) // Away win
      } else {
        teams(homeTeam).addPoints(1) // Draw
        teams(awayTeam).addPoints(1) // Draw
      }
    }

    formatRanking(teams.values.toList)
  }

  /**
   * Formats the ranking list for output.
   *
   * @param teams The list of teams.
   * @return A formatted string list of rankings.
   */
  private def formatRanking(teams: List[Team]): List[String] = {
    teams
      .groupBy(_.points)
      .toList
      .sortBy(-_._1) // Sort by points descending
      .zipWithIndex
      .flatMap { case (group, index) =>
        val rank = index + 1
        group._2.sortBy(_.name).map { team =>
          val pointStr = if (team.points == 1) "pt" else "pts"
          s"$rank. ${team.name}, ${team.points} $pointStr"
        }
      }
  }
}
