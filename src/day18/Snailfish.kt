package day18

import java.io.File

private val input = File("src/day18/input.txt").readLines()

fun main() {
    val snailfishNumbers = input.map { parse(MathString(it)) }
    println(snailfishNumbers.reduce(::addition))
}

fun parse(line: MathString): SnailfishNumber {
    var next = line.read()
    if (next != '[') {
        return SnailfishNumber(value = Character.getNumericValue(next))
    }
    val left = parse(line)
    next = line.read()
    assert(next == ',')
    val right = parse(line)
    next = line.read()
    assert(next == ']')
    return SnailfishNumber(left, right)
}

fun addition(left: SnailfishNumber, right: SnailfishNumber): SnailfishNumber = SnailfishNumber(left, right)

data class SnailfishNumber(
    var left: SnailfishNumber? = null,
    var right: SnailfishNumber? = null,
    var value: Int? = null
) {
    override fun toString(): String =
        if (value != null) {
            value.toString()
        } else {
            "[${left.toString()}, ${right.toString()}]"
        }

}

class MathString(private val string: String) {
    var index = 0

    fun read(): Char {
        val c = string.substring(index, index + 1)
        index++
        return c.first()
    }

    override fun toString(): String = string.substring(index)
}