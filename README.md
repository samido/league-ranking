# League Ranking

## Overview
This is a command-line application written in Scala that calculates the ranking of teams in a league based on the results of their matches.

## Requirements
- Java 17 or later
- Maven 3.6 or later
- Scala 2.13.15

## Getting Started

### Cloning the Repository
To clone the repository, use the following command:

```bash
git clone https://github.com/samido/league-ranking.git
cd league-ranking

```
### Building the Project
To build the project, run:

```bash
mvn clean install

```

### Running the Application
To run the application, provide an input file with the match results:

```bash
scala -cp target/scala-2.13/league-ranking_2.13-1.0-SNAPSHOT.jar com.example.Main input.txt
```

### Testing the Application
To run the unit tests, execute:

```bash
mvn test
```

### Input Format
The input should consist of lines formatted as follows:

```bash
TeamA scoreA, TeamB scoreB
```


### Example Input

```bash
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts
```

## Design Patterns Used
	-Strategy Pattern: Used in the RankingService to encapsulate the ranking logic.
	-Singleton Pattern: The RankingService is designed to be used as a single instance.
	-Factory Method: Used in the Team class to create teams based on results.

## Conclusion
This application is designed to be maintainable and testable, with a focus on clear structure and readability. The solution has been thoroughly tested and documented.

```bash

### Additional Input Tests

1. **Input with all teams having same points:**

```

### A 1, B 1 C 1, D 1 E 1, F 1

```bash

### Additional Input Tests


2. **Input with complex scores:**


```

### Team1 2, Team2 3 Team1 4, Team3 2 Team2 2, Team3 2