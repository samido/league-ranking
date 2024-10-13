package com.league.ranking

import scala.collection.mutable
import scala.util.Try

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
      val trimmedResult = result.trim
      println(s"Processing result: $trimmedResult")

      // Split the result string into home and away parts
      trimmedResult.split(", ") match {
        case Array(homeResult, awayResult) =>
          // Split the home result into parts, but treat the last part as the score
          val homeParts = homeResult.split(" ")
          val awayParts = awayResult.split(" ")

          // Check that each has at least one score
          if (homeParts.length < 2 || awayParts.length < 2) {
            println(s"Invalid format for match result: $trimmedResult")
            return List() // Early exit or continue
          }

          // Join all but the last part for the team name
          val homeTeam = homeParts.dropRight(1).mkString(" ")
          val homeScoreStr = homeParts.last
          val awayTeam = awayParts.dropRight(1).mkString(" ")
          val awayScoreStr = awayParts.last

          // Parse scores safely
          val homeScoreOpt = Try(homeScoreStr.toInt).toOption
          val awayScoreOpt = Try(awayScoreStr.toInt).toOption

          (homeScoreOpt, awayScoreOpt) match {
            case (Some(homeScore), Some(awayScore)) =>
              // Update home team points
              teams.getOrElseUpdate(homeTeam, Team(homeTeam))
              teams.getOrElseUpdate(awayTeam, Team(awayTeam))

              // Update points based on match outcome
              if (homeScore > awayScore) {
                teams(homeTeam).addPoints(3) // Home win
              } else if (homeScore < awayScore) {
                teams(awayTeam).addPoints(3) // Away win
              } else {
                teams(homeTeam).addPoints(1) // Draw
                teams(awayTeam).addPoints(1) // Draw
              }
            case _ =>
              println(s"Invalid score format in result: $trimmedResult")
          }
        case _ =>
          println(s"Invalid match result format: $trimmedResult")
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
