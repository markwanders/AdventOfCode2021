package day17

import java.io.File

private val input = File("src/day17/input.txt").readText()
val matches = "-?\\d+".toRegex().findAll(input).map { it.value.toInt() }.iterator()
val xRange = matches.next()..matches.next()
val yRange = matches.next()..matches.next()

fun main() {
    val maxY = (0..1000).maxOf { velocityX ->
        (0..1000).maxOf { velocityY ->
            maximumHeight(velocityX, velocityY)
        }
    }
    println(maxY)
}

fun maximumHeight(initVelocityX: Int, initVelocityY: Int): Int {
    var positionX = 0
    var positionY = 0
    var velocityX = initVelocityX
    var velocityY = initVelocityY
    var maxY = 0
    while (positionX < xRange.maxOf { it } && positionY > yRange.minOf { it }) {
        positionX += velocityX
        positionY += velocityY
        velocityY--
        if (velocityX > 0) {
            velocityX--
        } else {
            velocityX++
        }
        if (positionY > maxY) {
            maxY = positionY
        }
        if (positionX in xRange && positionY in yRange) {
            return maxY
        }
    }
    return 0
}