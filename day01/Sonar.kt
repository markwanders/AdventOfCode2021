import java.io.File

private val input = File("day01/input.txt").readLines().map { it.toInt() }

fun main() {
    println("Part 1: ${input.filterIndexed { i, s -> i > 0 && s > input[i - 1] }.size}")
    // Use the fact that A + B + C > B + C + D if D > A
    println("Part 2: ${input.filterIndexed { i, s -> i < input.size - 3 && s < input[i + 3] }.size}")
}