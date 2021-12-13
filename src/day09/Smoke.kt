package day09

import java.io.File

private val input = File("src/day09/input.txt").readLines().map { it.toCharArray() }

fun main() {
    val minima = mutableListOf<Pair<Int, Int>>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] < neighbors(y, x).minOf { input[it.first][it.second] }) {
                minima += y to x
            }
        }
    }
    println(minima.sumOf { 1 + Character.getNumericValue(input[it.first][it.second]) })
    val basinSizes = minima.map { minimum ->
        val seen = mutableSetOf(minimum)
        val queue = mutableListOf(minimum)
        while (queue.isNotEmpty()) {
            val position = queue.removeLast()
            seen.add(position)
            queue.addAll(neighbors(position.first, position.second)
                .filter { input[it.first][it.second] != '9' }
                .filter { it !in seen })
        }
        seen.size
    }
    println(basinSizes.sortedDescending().take(3).reduce { a, b -> a * b })
}

private fun neighbors(y: Int, x: Int): List<Pair<Int, Int>> =
    listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1)
        .filter { coordinates -> coordinates.first in input[y].indices && coordinates.second in input.indices }
        .map { coordinate -> coordinate.second to coordinate.first }
