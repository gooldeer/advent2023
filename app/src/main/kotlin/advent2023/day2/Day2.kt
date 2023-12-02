package advent2023.day2

import java.io.File

private fun gamesStatistics() = File("input/day2.txt")
    .readLines()
    .map(String::toGame)

fun day2FirstStar(
    maxRed: Int = 12,
    maxGreen: Int = 13,
    maxBlue: Int = 14
) = gamesStatistics()
    .filter { game -> game.sessions.all { it.withinLimits(maxRed, maxGreen, maxBlue) } }
    .sumOf { game -> game.id }

fun day2SecondStar() = gamesStatistics()
    .map { game -> game.maxRed * game.maxGreen * game.maxBlue }
    .sum()

private fun String.toGame() : Game = Game(
    id = "Game (\\d+):".toRegex().firstValueIn(this)?.toIntOrNull() ?: 0,
    sessions = split(";").map(String::toSession)
)

private val Game.maxRed
    get() = sessions.maxOf { it.red }

private val Game.maxGreen
    get() = sessions.maxOf { it.green }

private val Game.maxBlue
    get() = sessions.maxOf { it.blue }

private fun String.toSession() : Session = Session(
    red = "(\\d+) red".toRegex().firstValueIn(this)?.toIntOrNull() ?: 0,
    green = "(\\d+) green".toRegex().firstValueIn(this)?.toIntOrNull() ?: 0,
    blue = "(\\d+) blue".toRegex().firstValueIn(this)?.toIntOrNull() ?: 0,
)

private fun Regex.firstValueIn(string: String) = find(string)?.groupValues?.getOrNull(1)

private fun Session.withinLimits(
    maxRed: Int,
    maxGreen: Int,
    maxBlue: Int
) = red <= maxRed && green <= maxGreen && blue <= maxBlue

private data class Session(
    val red: Int,
    val green: Int,
    val blue: Int
)

private data class Game(
    val id: Int,
    val sessions: List<Session>
)