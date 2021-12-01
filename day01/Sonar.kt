import java.io.File

private val input = File("day01/input.txt").readLines().map { it.toInt() }

fun main() {
    println("Part 1: ${input.filterIndexed { i, s -> i > 0 && s > input[i - 1] }.size}")
    println("Part 2: ${input.filterIndexed { i, s -> i > 0 && i < input.size - 2 && s + input[i + 1] + input[i + 2] > input[i - 1] + s + input[i + 1] }.size}")
}