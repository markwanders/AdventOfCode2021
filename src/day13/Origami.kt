package day13

import java.io.File

private val input = File("src/day13/input.txt").readLines()

fun main() {
    val initialDots = mutableSetOf<Pair<Int, Int>>()
    val folds = mutableListOf<Pair<Char, Int>>()
    input.forEach { line ->
        "fold along ([xy])=([0-9]*)".toRegex().find(line)?.run {
            folds.add(destructured.component1().first() to destructured.component2().toInt())
        }
        "([0-9]*),([0-9]*)".toRegex().find(line)?.run {
            initialDots.add(destructured.component1().toInt() to destructured.component2().toInt())
        }
    }
    folds.foldIndexed(initialDots) { index, dotsAcc, (axis, coordinate) ->
        dotsAcc.map { dot ->
            when {
                axis == 'y' && dot.second > coordinate -> dot.first to 2 * coordinate - dot.second
                axis == 'x' && dot.first > coordinate -> 2 * coordinate - dot.first to dot.second
                else -> dot
            }
        }.toMutableSet().also {
            if (index == 1) println(dotsAcc.size)
        }
    }.let { foldedDots ->
        for (y in 0..foldedDots.maxOf { it.second }) {
            for (x in 0..foldedDots.maxOf { it.first }) {
                if(x to y in foldedDots) print("##") else print("  ")
            }
            println()
        }
    }

}