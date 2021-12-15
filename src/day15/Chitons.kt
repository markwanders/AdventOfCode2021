package day15

import java.io.File

private val input = File("src/day15/input.txt").readLines()

fun main() {
    val cave = hashMapOf<Pair<Int, Int>, Int>().withDefault { 0 }
    for (y in input.indices) {
        for (x in input[y].indices) {
            cave[y to x] = Character.getNumericValue(input[y][x])
        }
    }
    println(shortestPath(cave, cave.keys.maxByOrNull { it.first + it.second }!!))
}

fun shortestPath(map: MutableMap<Pair<Int, Int>, Int>, target: Pair<Int, Int>): Int {
    val queue = mutableListOf((Node(0 to 0, 0)))

    val distances = hashMapOf<Pair<Int, Int>, Int>().withDefault { 0 }

    while(queue.isNotEmpty()) {
        queue.sortBy { it.distance }
        val currentNode = queue.removeFirst()

        if(currentNode.position in distances.keys && distances.getValue(currentNode.position) <= currentNode.distance) {
            continue
        }

        distances[currentNode.position] = currentNode.distance

        if (currentNode.position == target) {
            return currentNode.distance
        }
        neighbors(map.keys.toList(), currentNode.position.first, currentNode.position.second).forEach {
            queue.add(Node(it, currentNode.distance + map.getValue(it)))
        }
    }
    return 0
}

private fun neighbors(map: List<Pair<Int, Int>>, y: Int, x: Int): List<Pair<Int, Int>> =
    listOf(y + 1 to x, y - 1 to x, y to x + 1, y to x - 1)
        .filter { coordinates -> coordinates in map }
        .map { coordinate -> coordinate.first to coordinate.second }

data class Node(val position: Pair<Int, Int>, val distance: Int)

