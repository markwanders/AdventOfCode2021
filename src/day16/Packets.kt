package day16

import java.io.File

private val input = File("src/day16/input.txt").readText().map { it.toString() }

var sum = 0
val binary = input.joinToString("") {
    it.toInt(16).toString(2).padStart(4, '0')
}
val parseQueue = mutableListOf(binary)

fun main() {
    while (parseQueue.isNotEmpty()) {
        parseQueue.removeFirst().let {
            if(it.contains('1')) {
                parse(it)
            }
        }
    }
    println("sum: $sum")
}

fun parse(string: String) {
    val version = string.take(3).toInt(2)
    sum += version
    val type = string.substring(3, 6).toInt(2)
    when (type) {
        4 -> {
            var literalValue = ""
            var i = 0
            while (true) {
                val part = string.substring(6 + i * 5, 11 + 5 * i)
                literalValue += part.drop(1)
                if (part.startsWith('0')) {
                    break
                } else {
                    i++
                }
            }
            parseQueue.add(string.substring(11 + 5 * i))
        }
        else -> {
            val lengthType = string.substring(6, 7).toInt(2)
            if (lengthType == 0) {
                val length = string.substring(7, 22).toInt(2)
                parseQueue.add(string.substring(22, 22 + length))
                parseQueue.add(string.substring(22 + length))
            } else if(lengthType == 1) {
                parseQueue.add(string.substring(18))
            }
        }
    }
}