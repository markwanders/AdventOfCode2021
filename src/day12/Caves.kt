package day12

import java.io.File

private val input = File("src/day12/input.txt").readLines()
private val caves = mutableMapOf<String, Set<String>>()
    .withDefault { setOf() }
    .apply {
        input.map { it.split("-") }.forEach { (a, b) ->
            put(a, getValue(a) + b)
            put(b, getValue(b) + a)
        }
    }

fun main() {
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
        caves.getOrDefault(position, mutableSetOf()).forEach { cave ->
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