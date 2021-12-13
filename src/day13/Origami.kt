package day13

import java.io.File

private val input = File("src/day13/input.txt").readLines()

fun main() {
    val dots = mutableSetOf<Pair<Int, Int>>()
    val folds = mutableListOf<Pair<Char, Int>>()
    input.forEach { line ->
        "fold along ([xy])=([0-9]*)".toRegex().find(line)?.run {
            folds.add(destructured.component1().first() to destructured.component2().toInt())
        }
        "([0-9]*),([0-9]*)".toRegex().find(line)?.run {
            dots.add(destructured.component1().toInt() to destructured.component2().toInt())
        }
    }
    folds.forEachIndexed { index, (axis, coordinate) ->
        when (axis) {
            'y' -> dots.filter { dot -> dot.second > coordinate }.forEach { (x, y) ->
                val folded = x to 2 * coordinate - y
                dots.add(folded)
                dots.remove(x to y)
            }
            'x' -> dots.filter { dot -> dot.first > coordinate }.forEach { (x, y) ->
                val folded = 2 * coordinate - x to y
                dots.add(folded)
                dots.remove(x to y)
            }
        }
        if (index == 0) {
            println(dots.size)
        }
    }
    val image = MutableList(dots.maxOf { it.second } + 1) { MutableList(dots.maxOf { it.first } + 1) { "  " }}
    dots.forEach { image[it.second][it.first] = "##" }
    image.forEach { y ->
        println(y.joinToString(""))
    }
}