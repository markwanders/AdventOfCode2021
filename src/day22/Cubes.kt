package day22

import java.io.File
import kotlin.math.max
import kotlin.math.min

private val input = File("src/day22/input.txt").readLines()

fun main() {
    val regex = "(on|off) x=([-\\d]*)..([-\\d]*),y=([-\\d]*)..([-\\d]*),z=([-\\d]*)..([-\\d]*)".toRegex()
    val cubes = mutableMapOf<Triple<Int, Int, Int>, Boolean>().withDefault { false }
    val rules = input.map {
        val (onOff, xMin, xMax, yMin, yMax, zMin, zMax) = regex.find(it)!!.destructured
        val on = onOff == "on"
        Rule(on, xMin.toInt(), xMax.toInt(), yMin.toInt(), yMax.toInt(), zMin.toInt(), zMax.toInt())
    }
    rules.forEach { rule ->
        for (x in rule.xMin..rule.xMax) {
            if (x in -50..50) {
                for (y in rule.yMin..rule.yMax) {
                    if (y in -50..50) {
                        for (z in rule.zMin..rule.zMax) {
                            if (z in -50..50) {
                                cubes[Triple(x, y, z)] = rule.on
                            }
                        }
                    }
                }
            }
        }
    }
    println(cubes.count { it.value })
    var cuboids = mutableMapOf<Cuboid, Int>()
    rules.forEach {
        val (on, rXMin, rXMax, rYMin, rYMax, rZMin, rZMax) = it
        val newCuboids = cuboids.toMutableMap().withDefault { 0 }
        cuboids.forEach { (cuboid, sign) ->
            val xMin = max(rXMin, cuboid.xMin)
            val xMax = min(rXMax, cuboid.xMax)
            val yMin = max(rYMin, cuboid.yMin)
            val yMax = min(rYMax, cuboid.yMax)
            val zMin = max(rZMin, cuboid.zMin)
            val zMax = min(rZMax, cuboid.zMax)
            if (xMin <= xMax && yMin <= yMax && zMin <= zMax) {
                 Cuboid(xMin, xMax, yMin, yMax, zMin, zMax).apply {
                     newCuboids[this] = newCuboids.getValue(this) - sign
                 }
            }
        }
        if (on) {
            Cuboid(rXMin, rXMax, rYMin, rYMax, rZMin, rZMax).apply {
                newCuboids[this] = newCuboids.getValue(this) + 1
            }
        }
        cuboids = newCuboids
    }
    val answer: Long =
        cuboids.entries.sumOf { (it, sign) -> sign.toLong() * (1 + it.xMax - it.xMin) * (1 + it.yMax - it.yMin) * (1 + it.zMax - it.zMin) }
    println(answer)
}

data class Rule(
    val on: Boolean,
    val xMin: Int,
    val xMax: Int,
    val yMin: Int,
    val yMax: Int,
    val zMin: Int,
    val zMax: Int
)

data class Cuboid(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int, val zMin: Int, val zMax: Int)