package day06

import java.io.File

private val input = File("src/day06/input.txt").readText().split(",").map { it.toInt() }

fun main() {
    println("Part 1: ${spawnFish(80)}")
    println("Part 2: ${spawnFish(256)}")
}

fun spawnFish(days: Int): Long =
    input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap().withDefault { 0 }.let { fish ->
        repeat(days) {
            val spawned = fish.getValue(0)
            for (i in 0..8) {
                fish[i] = fish.getValue(i + 1)
            }
            fish[6] = fish.getValue(6) + spawned
            fish[8] = fish.getValue(8) + spawned
        }
        fish.values.sum()
    }
