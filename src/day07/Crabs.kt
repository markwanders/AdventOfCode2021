package day07

import java.io.File
import kotlin.math.abs

private val input = File("src/day07/input.txt").readText().split(",").map { it.toInt() }

fun main() {
    val part1 = input.toSet()
        .associateBy({ it }, { input.sumOf { position -> abs(position - it) } })
        .minByOrNull { it.value }!!.value
    println("Part 1: $part1")
}