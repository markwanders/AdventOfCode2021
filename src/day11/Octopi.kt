package day11

import java.io.File

private val input = File("src/day11/input.txt").readLines().map { y -> y.split("")
    .filter { it.isNotEmpty() }.map { it.toInt() }}

fun main() {
    val octopi = mutableMapOf<Pair<Int, Int>, Int>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            octopi[y to x] = input[y][x]
        }
    }
    var flashCounter = 0
    val flashed = mutableSetOf<Pair<Int, Int>>()
    var step = 0
    while(flashed.size != 100) {
        step++
        flashed.clear()
        octopi.forEach { (k, v) -> octopi[k] = v + 1 }
        val toFlash = octopi.entries.filter { it.value > 9 }.map { it.key }.toMutableList()

        while (toFlash.isNotEmpty()) {
            val next = toFlash.removeLast()
            flashed.add(next)
            val neighbors = neighbors(next.first, next.second)
            neighbors.forEach {
                octopi[it] = octopi[it]!! + 1
            }
            toFlash.addAll(neighbors.filter { octopi[it]!! > 9 }.filterNot { it in flashed || it in toFlash })
        }
        flashed.forEach { octopi[it] = 0 }
        if (step <= 100) {
            flashCounter += flashed.size
        }
    }
    println(flashCounter)
    println(step)
}

private fun neighbors(y: Int, x: Int): List<Pair<Int, Int>> =
    listOf(x + 1 to y + 1, x - 1 to y - 1, x + 1 to y - 1, x - 1 to y + 1, x + 1 to y, x - 1 to y, x to y + 1, x to y - 1)
        .filter { coordinates -> coordinates.first in input[y].indices && coordinates.second in input.indices }
        .map { coordinate -> coordinate.second to coordinate.first }

