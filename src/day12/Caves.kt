package day12

import java.io.File

private val input = File("src/day12/input.txt").readLines()

fun main() {
    val caves = mutableMapOf<String, MutableList<String>>()
    input.forEach {
        val (first, last) = it.split("-").run { first() to last() }
        caves[first] = (caves.getOrDefault(first, mutableListOf()) + last) as MutableList<String>
        caves[last] = (caves.getOrDefault(last, mutableListOf()) + first) as MutableList<String>
    }
    val start = Path("start", mutableSetOf("start"))
    val queue = mutableListOf(start)
    var paths = 0
    while (queue.isNotEmpty()) {
        val (position, smallCaveHistory) = queue.removeFirst().run { position to smallCaveHistory }
        if (position == "end") {
            paths++
            continue
        }
        caves.getOrDefault(position, mutableListOf()).forEach { cave ->
            if(cave !in smallCaveHistory) {
                val newSmallCaveHistory = smallCaveHistory.toMutableSet()
                if(cave.lowercase() == cave) {
                    newSmallCaveHistory += cave
                }
                queue.add(Path(cave, newSmallCaveHistory))
            }
        }
    }
    println(paths)
}

data class Path(val position: String, val smallCaveHistory: MutableSet<String>)