package day12

import java.io.File

private val input = File("src/day12/input.txt").readLines()
private val caves = mutableMapOf<String, MutableList<String>>()

fun main() {
    input.forEach {
        val (first, last) = it.split("-").run { first() to last() }
        caves[first] = (caves.getOrDefault(first, mutableListOf()) + last) as MutableList<String>
        caves[last] = (caves.getOrDefault(last, mutableListOf()) + first) as MutableList<String>
    }
    println(caves)
    println(traverse())
    println(traverse(part2 = true))
}

fun traverse(part2: Boolean = false): Int {
    val start = Path("start", mutableSetOf("start"))
    val queue = mutableListOf(start)
    var paths = 0
    while (queue.isNotEmpty()) {
        val (position, smallCaveHistory, revisitedCave) =
            queue.removeFirst().run { Triple(position, smallCaveHistory, revisitedCave) }
        if (position == "end") {
            paths++
            continue
        }
        caves.getOrDefault(position, mutableListOf()).forEach { cave ->
            if (cave !in smallCaveHistory) {
                val newSmallCaveHistory = smallCaveHistory.toMutableSet()
                if (cave.lowercase() == cave) {
                    newSmallCaveHistory += cave
                }
                queue.add(Path(cave, newSmallCaveHistory, revisitedCave))
            } else if (part2) {
                if (cave in smallCaveHistory && revisitedCave == null && cave !in listOf("start", "end")) {
                    queue.add(Path(cave, smallCaveHistory, cave))
                }
            }
        }
    }
    return paths
}

data class Path(val position: String, val smallCaveHistory: MutableSet<String>, val revisitedCave: String? = null)