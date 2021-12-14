package day14

import java.io.File

private val input = File("src/day14/input.txt").readLines()

fun main() {
    val template = input.first()
    val rules = input.drop(2).associateBy({ it.split(" -> ").first() }, { it.split(" -> ").last() })
    var pairsCounter = template.zipWithNext { a, b -> a.toString() + b.toString() }.groupingBy { it }.eachCount()
        .mapValues { it.value.toLong() }
    val charCounter =
        template.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap().withDefault { 0L }
    repeat(40) { step ->
        if (step == 10) println("Part 1: ${charCounter.values.maxOrNull()!! - charCounter.values.minOrNull()!!}")
        val newPairsCounter = mutableMapOf<String, Long>().withDefault { 0L }
        pairsCounter.filter { it.value > 0 }.forEach { (pair, count) ->
            if (pair in rules) {
                val newPairs = pair.first() + rules.getValue(pair) to rules.getValue(pair) + pair.last()
                newPairsCounter[newPairs.first] = newPairsCounter.getValue(newPairs.first) + count
                newPairsCounter[newPairs.second] = newPairsCounter.getValue(newPairs.second) + count
                charCounter[rules.getValue(pair).first()] = charCounter.getValue(rules.getValue(pair).first()) + count
            }
        }
        pairsCounter = newPairsCounter
    }
    println("Part 2: ${charCounter.values.maxOrNull()!! - charCounter.values.minOrNull()!!}")
}