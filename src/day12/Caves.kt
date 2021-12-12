package day12

import java.io.File

private val input = File("src/day12/input.txt").readLines()

fun main() {
    val caves = mutableMapOf<String, MutableList<String>>()
    input.forEach {
        val parts = it.split("-")
        caves[parts.first()] = (caves.getOrDefault(parts.first(), mutableListOf()) + parts.last()) as MutableList<String>
    }
    println(caves)
    var position = "start"
    while (position != "end") {
        val nextSteps = caves[position]
    }
}