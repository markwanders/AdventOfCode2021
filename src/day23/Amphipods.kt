package day23

import java.io.File
import java.util.*

private val input = File("src/day23/input.txt").readLines()
val targetNodes: Map<Char, List<Pair<Int, Int>>> = mapOf(
    'A' to listOf(Pair(3, 3), Pair(3, 2)),
    'B' to listOf(Pair(5, 3), Pair(5, 2)),
    'C' to listOf(Pair(7, 3), Pair(7, 2)),
    'D' to listOf(Pair(9, 3), Pair(9, 2))
)

fun main() {
    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    val amphipods = mutableListOf<Amphipod>()
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            grid[x to y] = c
            if(c in listOf('A', 'B', 'C', 'D')) amphipods.add(Amphipod(x, y, Amphipod.Type.valueOf(c.toString())))
        }
    }
    println(grid)
    path(amphipods)
}


fun path(startingPositions: List<Amphipod>): Int {
    val queue = PriorityQueue(Comparator.comparing(State::totalCost))
    queue.add(State(startingPositions, 0))

    val seenStates = hashMapOf<State, Int>()

    while (queue.isNotEmpty()) {
        val currentState = queue.poll()

        if (currentState in seenStates.keys && seenStates[currentState]!! <= currentState.totalCost) {
            continue
        }
        seenStates[currentState] = currentState.totalCost

        if (currentState.amphipods.all { it.isHome() }) {
            return currentState.totalCost
        }
    }
    return 0
}

data class Amphipod(var x: Int, var y: Int, val type: Type) {
    fun isHome(): Boolean {
        return x to y in targetNodes[this.type.toString().first()]!!
    }
    enum class Type(val cost: Int) {
        A(1),
        B(10),
        C(100),
        D(1000)
    }
}

data class State(val amphipods: List<Amphipod>, var totalCost: Int)