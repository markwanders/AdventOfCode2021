package day08

import java.io.File
import kotlin.math.sign

private val input = File("src/day08/input.txt").readLines()

fun main() {
    val signalsToDigits = input.associateBy({ it.split(" | ").first() }, { it.split(" | ").last() })
    val counts = mutableMapOf(1 to 0, 4 to 0, 7 to 0, 8 to 0)
    signalsToDigits.forEach { (_, d) ->
        val digits = d.split(" ")
        digits.forEach { digit ->
            when (digit.length) {
                2 -> counts[1] = counts[1]!! + 1
                3 -> counts[7] = counts[7]!! + 1
                4 -> counts[4] = counts[4]!! + 1
                7 -> counts[8] = counts[8]!! + 1
            }
        }
    }
    println(counts.values.sum())
    var part2 = 0
    signalsToDigits.forEach { (s, d) ->
        val mapping = mutableMapOf<Char, String>()
        val digits = d.split(" ")
        val signals = s.split(" ")
        signals.forEach { signal ->
            when (signal.length) {
                2 -> mapping['1'] = signal
                3 -> mapping['7'] = signal
                4 -> mapping['4'] = signal
                7 -> mapping['8'] = signal
            }
        }
        mapping['9'] = signals.first { it.toSet().containsAll(mapping['7']!!.toSet() + mapping['4']!!.toSet()) && it.length == 6 }
        mapping['6'] = signals.first { six -> six.length == 6 && mapping['1']!!.toSet().any { it in mapping['8']!!.toSet() - six.toSet() } }
        mapping['2'] = signals.first { two -> mapping['1']!!.toSet().any { it in mapping['6']!!.toSet() - two.toSet() } }
        mapping['5'] = signals.first { five -> five !in mapping.values && five.length == 5 && mapping['1']!!.toSet().any { it in mapping['9']!!.toSet() - five.toSet() } }
        mapping['0'] = signals.first { zero -> zero.length == 6 && zero !in mapping.values }
        mapping['3'] = signals.first { three -> three !in mapping.values}
        val signalsToNumbers = mapping.entries.associateBy({ it.value.toCharArray().sorted().joinToString("") }) { it.key }
        val number = digits.map { digit -> signalsToNumbers[digit.toCharArray().sorted().joinToString("")] }.joinToString("").toInt()
        part2 += number

    }
    println("Part 2: $part2")
}