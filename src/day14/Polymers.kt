package day14

import java.io.File

private val input = File("src/day14/input.txt").readLines()

fun main() {
    var template = input.first()
    val rules = input.drop(2).associateBy({it.split(" -> ").first()}, {it.split(" -> ").last()})
    repeat(10) {
        template = template.toList()
            .zipWithNext { a, b -> a.toString() + b.toString() }
            .joinToString("") { if (it in rules.keys) it.first() + rules[it]!! else it }.plus(template.last())
    }
    val counts = template.toList().associateBy { char -> template.count { it == char } }
    println(counts.keys.maxOrNull()!! - counts.keys.minOrNull()!!)
}