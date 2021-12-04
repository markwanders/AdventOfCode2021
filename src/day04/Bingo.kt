package day04

import java.io.File

private val input = File("src/day04/input.txt").readText().split("\r\n\r\n")

fun main() {
    val numbers = input.first().split(",").map { it.toInt() }
    val boards = input.drop(1).map { line ->
        Board(
            line.split("\r\n")
                .map { row ->
                    row.trim().replace("  ", " ").split(" ").map { it.toInt() }
                }
        )
    }
    val winningValues = mutableSetOf<Int>()
    var iterator = numbers.iterator()
    while (!boards.map { it.isWinner(winningValues) }.contains(true)) {
        val numberToMark = iterator.next()
        boards.forEach { it.markNumber(numberToMark) }
    }
    println("Part 1: ${winningValues.first()}")

    iterator = numbers.iterator()
    while (boards.map { it.isWinner(winningValues) }.contains(false)) {
        val numberToMark = iterator.next()
        boards.filter { !it.isWinner(winningValues) }.forEach { it.markNumber(numberToMark) }
    }
    println("Part 2: ${winningValues.last()}")
}

class Board(private val rows: List<List<Int>>) {
    private val markedNumbers = mutableSetOf<Int>()
    private var won = false

    fun isWinner(winningValues: MutableSet<Int>): Boolean {
        return if (won) {
            true
        } else {
            val winner = this.rows.any { row -> row.all { it in markedNumbers } } ||
                    this.columns().any { column -> column.all { it in markedNumbers } }
            if (winner) {
                winningValues.add(rows.flatten().filter { it !in markedNumbers }.sum() * markedNumbers.last())
                this.won = true
            }
            won
        }
    }

    fun markNumber(number: Int) {
        markedNumbers.add(number)
    }

    private fun columns(): List<List<Int>> = rows.indices.map { columnIndex -> rows.map { it[columnIndex] } }

}