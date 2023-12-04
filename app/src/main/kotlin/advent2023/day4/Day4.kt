package advent2023.day4

import java.io.File
import kotlin.math.pow

class Day4 {
    
    private val indexRegex = "Card\\s+(\\d+): ".toRegex()

    private val input = File("input/day4.txt").readLines()

    private val cards = input
        .map { line ->
            val index = indexRegex.find(line)?.groupValues?.get(1)?.toIntOrNull() ?: -1
            val split = line.replace(indexRegex, "").split("|")
            val winningNums = split[0].split(" ").mapNotNull { it.toIntOrNull() }
            val myNums = split[1].split(" ").mapNotNull { it.toIntOrNull() }
            
            Card(index, winningNums, myNums)
        }
    
    private val collectedCards = cards.toMutableList()
    
    fun allWinningPoints() = cards.sumOf { it.points }
    
    fun giveMeAllTheCards() : Int {
        
        cards.forEach { it.wins() }
        
        return collectedCards.size
    }
    
    private fun Card.wins() {
        val winningCards = if (countOfWins > 0) cards.subList(index, index + countOfWins) else emptyList()
        collectedCards.addAll(winningCards)
        winningCards.forEach { it.wins() }
    }
}

private data class Card(
    val index: Int,
    val winningNums: List<Int>,
    val myNums: List<Int>
) {
    
    val countOfWins: Int
        get() = winningNums
            .map { myNums.contains(it) }
            .count { it }
    val points: Int
        get() = countOfWins
                .let { if (it < 1) 0 else 2.0.pow(it - 1.0).toInt() }
}