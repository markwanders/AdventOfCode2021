package day20

import java.io.File

private val input = File("src/day20/input.txt").readLines()

fun main() {
    val algorithm = input.first().toCharArray()
    var image = input.drop(2)
        .mapIndexed { y, line ->
            line.mapIndexed { x, _ -> y to x }.associateBy({ it.first to it.second }, { line[it.second] })
        }
        .flatMap { it.entries }
        .associate { it.key to it.value }
        .toMutableMap()
        .withDefault { '.' }
    repeat(50) {
        val even = (it + 1)%2 == 0
        val newImage = mutableMapOf<Pair<Int, Int>, Char>().withDefault { if(even) '.' else '#' }
        val yRange = image.keys.minOf { it.first } to image.keys.maxOf { it.first }
        val xRange = image.keys.minOf { it.second } to image.keys.maxOf { it.second }
        for (y in yRange.first - 2..yRange.second + 2) {
            for (x in xRange.first - 2..xRange.second + 2) {
                newImage[y to x] = algorithm[square(y, x, image)]
            }
        }
        image = newImage
        if(it == 1 || it == 49) {
            println(image.values.sumOf { if(it == '#') 1L else 0 })
        }
    }
}

fun square(y: Int, x: Int, image: Map<Pair<Int, Int>, Char>): Int {
    val string =
        listOf(
            image.getValue(y - 1 to x - 1), image.getValue(y - 1 to x), image.getValue(y - 1 to x + 1),
            image.getValue(y to x - 1), image.getValue(y to x), image.getValue(y to x + 1),
            image.getValue(y + 1 to x - 1), image.getValue(y + 1 to x), image.getValue(y + 1 to x + 1)
        ).joinToString("")
    return string.map { if (it == '#') '1' else '0' }.joinToString("").toInt(2)
}
