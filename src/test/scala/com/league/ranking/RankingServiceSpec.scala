package com.league.ranking

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RankingServiceSpec extends AnyFlatSpec with Matchers {

  "A RankingService" should "calculate the correct rankings" in {
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
}
