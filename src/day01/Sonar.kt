package day01

import java.io.File

private val input = File("src/day01/input.txt").readLines().map { it.toInt() }

fun main() {
    println("Part 1: ${input.zipWithNext { a, b -> b > a }.sumOf { it.compareTo(false) }}")
    // Use the fact that A + B + C > B + C + D if D > A
    println("Part 2: ${input.zip(input.drop(3)).filter { (a, b) -> b > a }.size}")
}