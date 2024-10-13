package com.league.ranking

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RankingServiceSpec extends AnyFlatSpec with Matchers {

  "A RankingService" should "calculate the correct rankings for typical results" in {
    val service = new RankingService()
    val results = List(
      "Lions 3, Snakes 3",
      "Tarantulas 1, FC Awesome 0",
      "Lions 1, FC Awesome 1",
      "Tarantulas 3, Snakes 1",
      "Lions 4, Grouches 0"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. Tarantulas, 6 pts",
      "2. Lions, 5 pts",
      "3. FC Awesome, 1 pt",
      "3. Snakes, 1 pt",
      "5. Grouches, 0 pts"
    )
  }

  it should "handle ties correctly" in {
    val service = new RankingService()
    val results = List(
      "A 1, B 1",
      "C 1, D 1",
      "E 0, F 0"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. A, 1 pt",
      "1. B, 1 pt",
      "1. C, 1 pt",
      "1. D, 1 pt",
      "5. E, 0 pts",
      "5. F, 0 pts"
    )
  }

  it should "handle single match results correctly" in {
    val service = new RankingService()
    val results = List("TeamA 3, TeamB 0")

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List("1. TeamA, 3 pts", "2. TeamB, 0 pts")
  }

  it should "handle multiple matches with the same score correctly" in {
    val service = new RankingService()
    val results = List(
      "TeamA 2, TeamB 2",
      "TeamC 2, TeamD 2",
      "TeamE 0, TeamF 0"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. TeamA, 1 pt",
      "1. TeamB, 1 pt",
      "1. TeamC, 1 pt",
      "1. TeamD, 1 pt",
      "5. TeamE, 0 pts",
      "5. TeamF, 0 pts"
    )
  }

  it should "correctly parse team names with spaces" in {
    val service = new RankingService()
    val results = List(
      "FC Awesome 3, Superstars 2",
      "FC United 2, FC Awesome 3"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. FC Awesome, 6 pts",
      "2. Superstars, 0 pts",
      "3. FC United, 0 pts"
    )
  }

  it should "handle invalid score formats gracefully" in {
    val service = new RankingService()
    val results = List(
      "TeamA 3, TeamB not-a-number",
      "TeamC 2, TeamD 1"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. TeamC, 3 pts",
      "2. TeamD, 1 pt",
      "3. TeamA, 0 pts",
      "4. TeamB, 0 pts"
    )
  }

  it should "handle empty results gracefully" in {
    val service = new RankingService()
    val results = List.empty[String]

    val rankings = service.calculateRanking(results)

    rankings shouldBe empty
  }

  it should "ignore malformed match results" in {
    val service = new RankingService()
    val results = List(
      "TeamA 3, TeamB 2",
      "Malformed Input",
      "TeamC 2, TeamD 1"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. TeamA, 3 pts",
      "2. TeamC, 2 pts",
      "3. TeamD, 1 pt",
      "4. TeamB, 0 pts"
    )
  }

  it should "handle matches with scores of zero" in {
    val service = new RankingService()
    val results = List(
      "TeamA 0, TeamB 0",
      "TeamC 0, TeamD 0"
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. TeamA, 1 pt",
      "1. TeamB, 1 pt",
      "3. TeamC, 1 pt",
      "3. TeamD, 1 pt"
    )
  }

  it should "handle duplicate results correctly" in {
    val service = new RankingService()
    val results = List(
      "Lions 3, Snakes 2",
      "Lions 3, Snakes 2" // Duplicate match
    )

    val rankings = service.calculateRanking(results)

    rankings should contain theSameElementsAs List(
      "1. Lions, 6 pts",
      "2. Snakes, 0 pts"
    )
  }
}
