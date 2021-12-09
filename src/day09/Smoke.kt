package day09

import java.io.File

private val input = File("src/day09/input.txt").readLines().map { it.toCharArray() }

fun main() {
    var minima = 0
    (input.indices).forEach { y ->
        (input[y].indices).forEach { x ->
            val neighbors = listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1).filter { coordinates ->
                coordinates.first in input[y].indices && coordinates.second in input.indices
            }.map { coordinate -> input[coordinate.second][coordinate.first] }
            if (input[y][x] < neighbors.minByOrNull { Character.getNumericValue(it) }!!) {
                minima += (1 + Character.getNumericValue(input[y][x]))
            }
        }
    }
    println(minima)
}