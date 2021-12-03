package day03

import java.io.File

private val input = File("src/day03/input.txt").readLines()

fun main() {
    var gamma = ""
    var epsilon = ""
    for(i in input.first().indices) {
        val ones = input.count { bit -> bit[i] == '1' }
        val zeroes = input.count { bit -> bit[i] == '0' }
        if (zeroes > ones) {
            gamma += '0'
            epsilon += 1
        } else {
            gamma += '1'
            epsilon += '0'
        }
    }
    println(gamma.toInt(2).times(epsilon.toInt(2)))
}