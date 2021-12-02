package day02

import java.io.File

private val input = File("src/day02/input.txt").readLines()

fun main() {
    var x = 0
    var y = 0
    input.forEach {
        val step = it.split(" ")
        when (step[0]) {
            "forward" -> x += step[1].toInt()
            "down" -> y += step[1].toInt()
            "up" -> y -= step[1].toInt()
        }
    }
    println("Part 1: ${x * y}")
    x = 0
    y = 0
    var aim = 0
    input.forEach {
        val step = it.split(" ")
        when (step[0]) {
            "forward" -> {
                x += step[1].toInt()
                y += aim * step[1].toInt()
            }
            "down" -> aim += step[1].toInt()
            "up" -> aim -= step[1].toInt()
        }
    }
    println("Part 2: ${x * y}")
}