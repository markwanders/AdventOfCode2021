package day22

import java.io.File

private val input = File("src/day22/input.txt").readLines()

fun main() {
    val regex = "(on|off) x=([-\\d]*..[-\\d]*),y=([-\\d]*..[-\\d]*),z=([-\\d]*..[-\\d]*)".toRegex()
    val cubes = mutableMapOf<Triple<Int, Int, Int>, Boolean>().withDefault { false }
    val rules = input.map {
        val (onOff, x, y, z) = regex.find(it)!!.destructured
        val on = onOff == "on"
        val xRange = x.split("..").first().toInt()..x.split("..").last().toInt()
        val yRange = y.split("..").first().toInt()..y.split("..").last().toInt()
        val zRange = z.split("..").first().toInt()..z.split("..").last().toInt()
        Rule(on, xRange, yRange, zRange)
    }
    rules.forEach { rule ->
        for (x in rule.x) {
            if (x in -50..50) {
                for (y in rule.y) {
                    if (y in -50..50) {
                        for (z in rule.z) {
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
}

data class Rule(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)