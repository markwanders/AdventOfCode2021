package day06

import java.io.File

private val input = File("src/day06/input.txt").readText().split(",").map { it.toInt() }.toMutableList()

fun main() {
    println("Part 1: ${spawnFish(80)}")
    println("Part 2: ${spawnFish(256)}")
}

fun spawnFish(days: Int): Long {
    val fish = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
    (0 until days).forEach { _ ->
        val spawned = fish.getOrDefault(0, 0)
        (0..8).forEach {
            fish[it] = fish.getOrDefault(it + 1, 0)
        }
        fish[6] = fish.getOrDefault(6, 0) + spawned
        fish[8] = fish.getOrDefault(8, 0) + spawned
    }
    return fish.values.sum()
}