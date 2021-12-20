package day19

import java.io.File

private val input = File("src/day19/input.txt").readLines()

fun main() {
    val scanners = mutableMapOf<Int, MutableList<Triple<Int, Int, Int>>>().withDefault { mutableListOf() }
    var nr = 0
    input.forEach {
        if (' ' !in it && it.isNotBlank()) {
            val parts = it.split(",").map { it.toInt() }
            scanners[nr] = scanners.getValue(nr).plus(Triple(parts[0], parts[1], parts[2])).toMutableList()
        } else if (it.isBlank()) {
            nr++
        }
    }
    println(scanners)
    scanners.entries.zipWithNext { a, b -> a.value }
}

fun distance(coordinate: Triple<Int, Int, Int>): Int {
    return coordinate.first * coordinate.first + coordinate.second * coordinate.second + coordinate.third * coordinate.third
}