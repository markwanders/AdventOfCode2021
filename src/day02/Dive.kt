package day02

import java.io.File

private val input = File("src/day02/input.txt").readLines()

fun main() {
    var x = 0
    var y = 0
    var aim = 0
    input.forEach { line ->
        line.split(" ").let { step ->
            when (step[0]) {
                "forward" -> {
                    x += step[1].toInt()
                    y += aim * step[1].toInt()
                }
                "down" -> aim += step[1].toInt()
                "up" -> aim -= step[1].toInt()
            }
        }
    }
    println("Part 1: ${x * aim}, Part 2: ${x * y}")
}