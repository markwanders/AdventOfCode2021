package day21

import kotlin.math.max
import kotlin.math.min

fun main() {
    var p1 = 10
    var p2 = 6
    var p1Score = 0
    var p2Score = 0
    var p1Turn = true
    var dice = listOf(1, 2, 3)
    var rolls = 0
    while (p1Score < 1000 && p2Score < 1000) {
        rolls += 3
        if (p1Turn) {
            p1 = (p1 + dice.sum()) % 10
            if (p1 == 0) p1 = 10
            p1Score += p1
            p1Turn = false
        } else {
            p2 = (p2 + dice.sum()) % 10
            if (p2 == 0) p2 = 10
            p2Score += p2
            p1Turn = true
        }
        dice = dice.map {
            var n = it + 3
            if (n > 100) {
                n %= 100
            }
            n
        }
    }
    val answer = min(p1Score, p2Score) * rolls
    println(answer)
    var p1Wins = 0L
    var p2Wins = 0L
    val dimensionCounter = mutableMapOf(Dimension(0, 0, p1, p2) to 1L).withDefault { 0 }
    while (dimensionCounter.isNotEmpty()) {
        val it = dimensionCounter.iterator()
        val (next, count) = it.next()
        it.remove()
        val (p1Score, p2Score, p1, p2) = next
        for (d1 in 1..3) {
            for (d2 in 1..3) {
                for (d3 in 1..3) {
                    var pp1 = (p1 + d1 + d2 + d3) % 10
                    if (pp1 == 0) pp1 = 10
                    val pp1Score = p1Score + pp1
                    if (pp1Score >= 21) {
                        p1Wins += count
                    } else {
                        for (dd1 in 1..3) {
                            for (dd2 in 1..3) {
                                for (dd3 in 1..3) {
                                    var pp2 = (p2 + dd1 + dd2 + dd3) % 10
                                    if (pp2 == 0) pp2 = 10
                                    val pp2Score = p2Score + pp2
                                    if (pp2Score >= 21) {
                                        p2Wins += count
                                    } else {
                                        val dimension = Dimension(pp1Score, pp2Score, pp1, pp2)
                                        dimensionCounter[dimension] = dimensionCounter.getValue(dimension) + count
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    println(max(p1Wins, p2Wins))
}

data class Dimension(
    var p1Score: Int = 0,
    var p2Score: Int = 0,
    var p1: Int = 0,
    var p2: Int = 0
)