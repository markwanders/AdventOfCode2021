package day08

import java.io.File

private val input = File("src/day08/input.txt").readLines()

fun main() {
    val signalsToDigits = input.associateBy( {it.split(" | ").first()}, {it.split(" | ").last()})
    val counts = mutableMapOf(1 to 0, 4 to 0, 7 to 0, 8 to 0)
    signalsToDigits.forEach { (s, d) ->
        val digits = d.split(" ")
        digits.forEach { digit ->
            when(digit.length) {
                2 -> counts[1] = counts[1]!! + 1
                3 -> counts[7] = counts[7]!! + 1
                4 -> counts[4] = counts[4]!! + 1
                7 -> counts[8] = counts[8]!! + 1
            }
        }
    }
    println(counts.values.sum())
}