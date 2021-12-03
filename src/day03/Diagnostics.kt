package day03

import java.io.File

private val input = File("src/day03/input.txt").readLines()

fun main() {
    var gamma = ""
    var epsilon = ""
    for (i in input.first().indices) {
        val ones = input.count { bit -> bit[i] == '1' }
        val zeroes = input.count { bit -> bit[i] == '0' }
        if (zeroes > ones) {
            gamma += '0'
            epsilon += '1'
        } else {
            gamma += '1'
            epsilon += '0'
        }
    }
    println(gamma.toInt(2).times(epsilon.toInt(2)))
    var oxy = input
    var co2 = input
    for (i in oxy.first().indices) {
        if(oxy.size > 1) {
            oxy = oxy.filter { it[i] == mostCommonBit(oxy.map { number -> number[i] }) }
        }
        if (co2.size > 1) {
            co2 = co2.filter { it[i] != mostCommonBit(co2.map { number -> number[i] }) }
        }
    }
    println(oxy.first().toInt(2).times(co2.first().toInt(2)))
}

fun mostCommonBit(list: List<Char>): Char =
    if (list.count { it == '1' } >= list.count { it == '0' }) {
        '1'
    } else {
        '0'
    }
