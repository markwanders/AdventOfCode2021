package day19

import java.io.File

private val input = File("src/day19/input.txt").readLines()

fun main() {
    val scanners = mutableMapOf<Int, MutableList<Triple<Int, Int, Int>>>().withDefault { mutableListOf() }
    var scanner = 0
    input.forEach {
        if(' ' !in it && it.isNotBlank()) {
            val parts = it.split(",").map { it.toInt() }
            scanners[scanner] = scanners.getValue(scanner).plus(Triple(parts[0], parts[1], parts[2])).toMutableList()
        } else if(it.isBlank()) {
            scanner++
        }
    }
    println(scanners)
}