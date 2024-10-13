package com.league.ranking

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
 * @author Sam Rabophala
 * @version 1.0
 * @since 2024-10-12
 * The entry point of the application.
 */
object Main {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Please provide an input file.")
      return
    }

    val filename = args(0)
    val rankingService = new RankingService()

    // Read input from file
    val result = Try(Source.fromFile(filename).getLines().toList) match {
      case Success(lines) => rankingService.calculateRanking(lines)
      case Failure(ex) =>
        println(s"Error reading file: ${ex.getMessage}")
        return
    }

    result.foreach(println)
  }
}
