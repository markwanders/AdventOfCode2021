package day15

import java.io.File
import java.util.*

private val input = File("src/day15/input.txt").readLines()

fun main() {
    val cave = hashMapOf<Pair<Int, Int>, Int>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            cave[y to x] = Character.getNumericValue(input[y][x])
        }
    }
    println(shortestPath(cave, cave.keys.maxByOrNull { it.first + it.second }!!))
    println(shortestPath(cave, input.size * 5 - 1 to input.size * 5 - 1))
}

fun shortestPath(map: MutableMap<Pair<Int, Int>, Int>, target: Pair<Int, Int>): Int {
    val queue = PriorityQueue(Comparator.comparing(Node::distance))
    queue.add((Node(0 to 0, 0)))
    val distances = hashMapOf<Pair<Int, Int>, Int>()

    while (queue.isNotEmpty()) {
        val currentNode = queue.poll()

        if (currentNode.position in distances.keys && distances.getValue(currentNode.position) <= currentNode.distance) {
            continue
        }

        distances[currentNode.position] = currentNode.distance

        if (currentNode.position == target) {
            return currentNode.distance
        }
        val max = map.keys.maxOf { it.first } + 1
        neighbors(currentNode.position.first, currentNode.position.second, target).forEach { (ny, nx) ->
            var riskValue = map.getValue(ny % max to nx % max) + ny / max + nx / max
            while (riskValue > 9) riskValue -= 9
            queue.add(Node(ny to nx, currentNode.distance + riskValue))
        }
    }
    return 0
}

private fun neighbors(y: Int, x: Int, target: Pair<Int, Int>): List<Pair<Int, Int>> =
    listOf(y + 1 to x, y - 1 to x, y to x + 1, y to x - 1)
        .filter { (y, x) -> y >= 0 && x >= 0 && y <= target.first && x <= target.second }

data class Node(val position: Pair<Int, Int>, val distance: Int)

