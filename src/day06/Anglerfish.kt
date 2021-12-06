package day06

import java.io.File

private val input = File("src/day06/input.txt").readText().split(",").map { it.toInt() }.toMutableList()

fun main() {
    (0..79).forEach { _ ->
        input.forEachIndexed { index, value -> input[index] = value - 1 }
        val newFish = input.count { it < 0 }
        input.forEachIndexed { index, value -> input[index] = if(value < 0) 6 else value }
        input.addAll(MutableList(newFish) { 8 })
    }
    println("Part 1: ${input.size}")
}