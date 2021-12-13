package day13

import java.io.File

private val input = File("src/day13/input.txt").readLines()

fun main() {
    val dots = mutableMapOf<Pair<Int, Int>, Int>().withDefault { 0 }
    val folds = mutableListOf<Pair<Char, Int>>()
    input.forEach { line ->
        "fold along ([xy])=([0-9]*)".toRegex().find(line)?.run {
            folds.add(destructured.component1().first() to destructured.component2().toInt())
        }
        "([0-9]*),([0-9]*)".toRegex().find(line)?.run {
            dots[destructured.component1().toInt() to destructured.component2().toInt()] = 1
        }
    }
    println(dots)
    println(folds)
    println(dots.values.sum())
    folds.forEach { (axis, coordinate) ->
        when (axis) {
            'y' -> dots.entries.filter { entry -> entry.key.second > coordinate }.forEach { (key, value) ->
                    val folded = key.first to 2 * coordinate - key.second
                    dots[folded] = dots.getValue(folded) + value
                    dots.remove(key)
                }

            'x' -> dots.entries.filter { entry -> entry.key.first > coordinate }.forEach { (key, value) ->
                    val folded = 2 * coordinate - key.first to key.second
                    dots[folded] = dots.getValue(folded) + value
                    dots.remove(key)
                }
        }
    }
    println(dots)
    println(dots.values.sum())
}