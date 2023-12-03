package advent2023.day3

import java.io.File

private class Day3Calculator {
    
    private val engineScheme = File("input/day3.txt")
        .readLines()

    private fun allPotentialParts() = engineScheme
        .mapIndexed { index, line ->
            val result = mutableListOf<PotentialPart>()
            var position: Int = 0
            line
                .split("\\D".toRegex())
                .onEach { number ->
                    when {
                        number.isBlank() -> position++
                        else -> {
                            result.add(PotentialPart(number, line.indexOf(number, position), index))
                            position += number.length + 1
                        }
                    }
                }
            result
        }

    private fun allSymbols() = engineScheme
        .mapIndexed { index, line ->
            val result = mutableListOf<Symbol>()
            line
                .split("\\d|\\.".toRegex())
                .filter { it.isNotEmpty() }
                .onEach { symbol ->
                    val previous = result
                        .lastOrNull { it.symbol == symbol }
                        ?.let { it.x + it.symbol.length } ?: 0
                    result.add(Symbol(symbol, line.indexOf(symbol, previous), index))
                }
            result
        }
    
    private val potentialParts = allPotentialParts().flatten()
    private val allSymbols = allSymbols().flatten().asSequence()
    
    private fun PotentialPart.isRealPart() : Boolean = adjacentTo() != null
    
    private fun PotentialPart.adjacentTo() : Symbol? = allSymbols
        .firstOrNull { it.x in (x - 1)..(x + number.length) && it.y in (y - 1)..(y + 1) }
    
    fun findGears() = findAllRealParts()
            .groupBy { it.adjacentTo()!! }
            .filterKeys { it.symbol == "*" }
            .filter { it.value.size == 2 }
            .mapValues { it.value.map { it.number.toInt() } }
            .mapValues { it.value.reduce { acc, curr -> acc * curr } }
            .values
    
    fun findAllRealParts() = potentialParts
        .filter { part -> part.isRealPart() }
}

fun day3Part1() = Day3Calculator()
    .findAllRealParts()
    .sumOf { it.number.toIntOrNull() ?: 0 }

fun day3Part2() = Day3Calculator()
    .findGears().sum()

private data class PotentialPart(
    val number: String,
    val x: Int,
    val y: Int,
)

private data class Symbol(
    val symbol: String,
    val x: Int,
    val y: Int,
)