import java.io.File

private val input = File("day01/input.txt").readLines().map { it.toInt() }

fun main() {
    println("Part 1: ${input.zipWithNext { a, b -> b > a }.sumOf { if (it) 1L else 0 }}")
    // Use the fact that A + B + C > B + C + D if D > A
    println("Part 2: ${input.zip(input.drop(3)).filter { (a, b) -> b > a }.size}")
}