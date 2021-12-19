package day18

import java.io.File

private val input = File("src/day18/input.txt").readLines()

fun main() {
    val snailfishNumbers: List<SnailfishNumber> = input.map { parse(MathString(it)) }
    snailfishNumbers.forEach { println(it) }
    val result: SnailfishNumber = snailfishNumbers.reduce(::addition)
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
    var depth: Int,
    var parent: SnailfishNumber? = null
) {
    init {
        left?.parent = this
        right?.parent = this
    }

    fun increaseDepth() {
        depth++
        left?.increaseDepth()
        right?.increaseDepth()
    }

    fun explode() {
       if(this.depth == 4 && value == null) {
           println("explode $this")
           println(this.parent)
           explodeLeft(this.left?.value)
           explodeRight(this.right?.value)
           this.left = null
           this.right = null
           this.value = 0
           return
       } else {
           left?.explode()
           right?.explode()
       }
    }

    private fun explodeLeft(value: Int?) {
        println("exploding to the left: $value")
        if (this.parent?.right?.value != null) {
            this.parent?.right?.value = this.parent?.right?.value!! + value!!
        } else {
            this.parent?.right?.right?.explodeLeft(value)
        }
    }

    private fun explodeRight(value: Int?) {
        println("exploding to the right: $value")
        if (this.parent?.left?.value != null) {
            this.parent?.left?.value = this.parent?.left?.value!! + value!!
        } else if(this.parent?.right?.value != null) {
            this.parent?.right?.value = this.parent?.right?.value!! + value!!
        }
        else {
            this.parent?.left?.left?.explodeRight(value)
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