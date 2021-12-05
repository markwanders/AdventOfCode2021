package day05

import java.io.File

private val input = File("src/day05/input.txt").readLines()

fun main() {
    var seen = mutableMapOf<Pair<Int, Int>, Int>()
    input.parse()
        .filter { line -> line.x1 == line.x2 || line.y1 == line.y2 }
        .drawLines(seen)
    println("Part 1: ${seen.values.count { it > 1 }}")
    seen = mutableMapOf()
    input.parse()
        .drawLines(seen)
    println("Part 2: ${seen.values.count { it > 1 }}")
}

fun List<String>.parse(): List<Line> = map { line ->
    val (x1, y1, x2, y2) = line.split(" -> ", ",").map { it.toInt() }
    Line(x1, y1, x2, y2)
}

fun List<Line>.drawLines(seen: MutableMap<Pair<Int, Int>, Int>) = forEach { line ->
    val dx = line.x2.compareTo(line.x1)
    val dy = line.y2.compareTo(line.y1)
    var x = line.x1
    var y = line.y1
    while (x != line.x2 + dx || y != line.y2 + dy) {
        seen[Pair(x, y)] = seen.getOrDefault(Pair(x, y), 0) + 1
        x += dx
        y += dy
    }
}

class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)