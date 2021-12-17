package day17

import java.io.File

private val input = File("src/day17/input.txt").readText()
val matches = "-?\\d+".toRegex().findAll(input).map { it.value.toInt() }.iterator()
val xRange = matches.next()..matches.next()
val yRange = matches.next()..matches.next()

fun main() {
    val velocities = (0..1000).map { velocityX ->
        (-1000..1000).associateBy({ velocityX to it },
            { maximumHeight(velocityX, it) })
    }.flatMap { it.entries }
        .filter { it.value >= yRange.minOf { it } }
        .associate { it.key to it.value }
    println(velocities.maxOf { it.value })
    println(velocities.keys.size)
}

fun maximumHeight(initVelocityX: Int, initVelocityY: Int): Int {
    var positionX = 0
    var positionY = 0
    var velocityX = initVelocityX
    var velocityY = initVelocityY
    val minY = yRange.minOf { it }
    var maxY = minY
    while (positionX <= xRange.maxOf { it } && positionY >= minY) {
        positionX += velocityX
        positionY += velocityY
        velocityY--
        if (velocityX > 0) {
            velocityX--
        } else if (velocityX < 0) {
            velocityX++
        }
        if (positionY > maxY) {
            maxY = positionY
        }
        if (positionX in xRange && positionY in yRange) {
            return maxY
        }
    }
    return minY - 1
}