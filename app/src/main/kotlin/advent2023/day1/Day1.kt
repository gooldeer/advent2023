package advent2023.day1

import java.io.File

private val regexStart : Regex = "(\\d|one|two|three|four|five|six|seven|eight|nine)".toRegex()
private val regexEnd : Regex = ".*(\\d|one|two|three|four|five|six|seven|eight|nine).*".toRegex()

private fun calibrationInput() = File("input/day1.txt")
    .readText()
    .split("\n")
    .asSequence()
    .map { result ->
        regexStart.find(result)?.groupValues?.getOrNull(1)?.toIntVerbal() to
        regexEnd.find(result)?.groupValues?.getOrNull(1)?.toIntVerbal()
    }
    .mapIndexed { index, pair ->
        "${pair.first ?: pair.second}${pair.second ?: pair.first}".also { println("${index + 1} $it") }
    }
    .mapNotNull(String::toIntOrNull)

private fun String.toIntVerbal() : Int? = toIntOrNull() ?: when(this) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> null
}

fun day1FirstStar() = calibrationInput().sum()


