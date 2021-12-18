package day18

import java.io.File

private val input = File("src/day18/input.txt").readLines()

fun main() {
    val snailfishNumbers = input.map { parse(MathString(it)) }
    val result = snailfishNumbers.reduce(::addition)
    result.explode()
    println(result)
}

fun parse(line: MathString): SnailfishNumber {
    var next = line.read()
    if (next != '[') {
        return SnailfishNumber(value = Character.getNumericValue(next), depth = line.depth)
    }
    line.down()
    val left = parse(line)
    next = line.read()
    assert(next == ',')
    val right = parse(line)
    next = line.read()
    assert(next == ']')
    line.up()
    return SnailfishNumber(left, right, depth = line.depth)
}

fun addition(left: SnailfishNumber, right: SnailfishNumber): SnailfishNumber {
    val depth = left.depth
    left.increaseDepth()
    right.increaseDepth()
    return SnailfishNumber(left, right, depth = depth)
}

data class SnailfishNumber(
    var left: SnailfishNumber? = null,
    var right: SnailfishNumber? = null,
    var value: Int? = null,
    var depth: Int
) {
    fun increaseDepth() {
        depth++
        left?.increaseDepth()
        right?.increaseDepth()
    }

    fun explode() {
        left?.let {
            if (it.depth == 4) {

            }
        }
    }

    override fun toString(): String =
        if (value != null) {
            value.toString()
        } else {
            "$depth:[${left.toString()}, ${right.toString()}]"
        }

}

class MathString(private val string: String) {
    private var index = 0
    var depth = 0

    fun down() {
        depth++
    }

    fun up() {
        depth--
    }

    fun read(): Char {
        val c = string.substring(index, index + 1)
        index++
        return c.first()
    }

    override fun toString(): String = string.substring(index)
}