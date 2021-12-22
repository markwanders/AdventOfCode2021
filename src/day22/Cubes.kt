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
    var cuboids = mutableMapOf<Cuboid, Int>()
    rules.forEach {
        val (on, rX, rY, rZ) = it
        val newCuboids = cuboids.toMutableMap().withDefault { 0 }
        cuboids.forEach {  (cuboid, sign) ->
            overlap(cuboid.x, rX)?.let { x ->
                overlap(cuboid.y, rY)?.let { y ->
                    overlap(cuboid.z, rZ)?.let { z ->
                        val overlap = Cuboid(x, y, z)
                        newCuboids[overlap] = newCuboids.getValue(overlap) - sign
                    }
                }
            }
        }
        if (on) {
            newCuboids[Cuboid(rX, rY, rZ)] = newCuboids.getValue(Cuboid(rX, rY, rZ)) + 1
        }
        cuboids = newCuboids
    }
    val answer: Long = cuboids.entries.sumOf { (it, sign) -> sign.toLong() * (1 + it.x.last - it.x.first) * (1 + it.y.last - it.y.first) * (1 + it.z.last - it.z.first) }
    println(answer)
}

fun overlap(a: IntRange, b: IntRange): IntRange? =
    a.intersect(b).let { int -> if(int.isNotEmpty()) int.first()..int.last() else null }


data class Rule(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)
data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange)