package day07

import java.io.File
import kotlin.math.abs

private val input = File("src/day07/input.txt").readText().split(",").map { it.toInt() }

fun main() {
    val part1 = IntRange(input.minOrNull()!!, input.maxOrNull()!!)
        .associateBy({ it }, { input.sumOf { position -> abs(position - it) } })
        .minByOrNull { it.value }!!.value
    println("Part 1: $part1")
    val part2 = IntRange(input.minOrNull()!!, input.maxOrNull()!!)
        .associateBy({ it }, { input.sumOf { position -> IntRange(1, abs(position - it)).sum() }})
        .minByOrNull { it.value }!!.value
    println("Part 2: $part2")
}