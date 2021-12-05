package day05

import java.io.File

private val input = File("src/day05/input.txt").readLines()

fun main() {
    val seen = mutableMapOf<Pair<Int, Int>, Int>()
    input.map { line -> Line(line.split(" -> ", ",").map { it.toInt() }) }
        .filter { line -> line.x1 == line.x2 || line.y1 == line.y2 }
        .forEach { line ->
            println("(${line.x1}, ${line.y1}) to (${line.x2}, ${line.y2})")
            if (line.y1 == line.y2) {
                for (x in line.x().minOrNull()!!..line.x().maxOrNull()!!) {
                    seen[Pair(x, line.y1)] = seen.getOrDefault(Pair(x, line.y1), 0) + 1
                }
            } else {
                for (y in line.y().minOrNull()!!..line.y().maxOrNull()!!) {
                    seen[Pair(line.x1, y)] = seen.getOrDefault(Pair(line.x1, y), 0) + 1
                }
            }
        }
    println(seen.values.count { it > 1 })
}

class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1], coordinates[2], coordinates[3])
    fun x() :List<Int> = listOf(x1, x2)
    fun y() :List<Int> = listOf(y1, y2)
}