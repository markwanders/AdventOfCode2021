package day21

fun main() {
    var p1 = 10
    var p2 = 6
    var p1Score = 0
    var p2Score = 0
    var p1Turn = true
    var dice = listOf(1, 2, 3)
    var rolls = 0
    while (true) {
        rolls += 3
        if (p1Turn) {
            p1 = (p1 + dice.sum()) % 10
            if (p1 == 0) p1 = 10
            p1Score += p1
            p1Turn = false
            println("Player 1 rolls $dice and moves to space $p1 for a total score of $p1Score")
        } else {
            p2 = (p2 + dice.sum()) % 10
            if (p2 == 0) p2 = 10
            p2Score += p2
            p1Turn = true
            println("Player 2 rolls $dice and moves to space $p2 for a total score of $p2Score")
        }
        if (p1Score >= 1000 || p2Score >= 1000) break
        dice = dice.map {
            var n = it + 3
            if(n > 100) {
                n %= 100
            }
            n
        }
    }
    val answer = (if(p1Score >= 1000) p2Score else p1Score) * rolls
    println(answer)
}
