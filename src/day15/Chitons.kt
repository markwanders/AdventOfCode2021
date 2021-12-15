package day15

import java.io.File

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
        neighbors(currentNode.position.first, currentNode.position.second, target).forEach { (ny, nx) ->
            val max = map.keys.maxOf { it.first } + 1
            val down =  ny / max
            val right = nx / max
            val projectedY = ny % max
            val projectedX = nx % max
            var riskValue = map.getValue(projectedY to projectedX) + down + right
            while (riskValue > 9) riskValue -= 9
            queue.add(Node(ny to nx, currentNode.distance + riskValue))
        }
    }
    return 0
}

private fun neighbors(y: Int, x: Int, target: Pair<Int, Int>): List<Pair<Int, Int>> =
    listOf(y + 1 to x, y - 1 to x, y to x + 1, y to x - 1)
        .filter { coordinates -> coordinates.first >= 0 && coordinates.second >= 0 && coordinates.first <= target.first && coordinates.second <= target.second}
        .map { coordinate -> coordinate.first to coordinate.second }

data class Node(val position: Pair<Int, Int>, val distance: Int)

