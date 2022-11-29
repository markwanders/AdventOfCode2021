package day18

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

private val input = File("src/day18/input.txt").readLines()

fun main() {
    println(input.map { parse(MathString(it)) }.reduce(::reduce).magnitude())
    println(input
            .flatMapIndexed { i, a ->
                input.mapIndexed { j, b ->
                    if (i != j) listOf(parse(MathString(a)), parse(MathString(b))).reduce(::reduce).magnitude() else 0
                }
            }
            .max())
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

fun reduce(left: SnailfishNumber, right: SnailfishNumber): SnailfishNumber {
    val result = addition(left, right)
    while (true) {
        if (explode(result)) {
            continue
        }
        if (split(result)) {
            continue
        }
        break
    }
    return result
}

fun addition(left: SnailfishNumber, right: SnailfishNumber): SnailfishNumber {
    val depth = left.depth
    left.increaseDepth()
    right.increaseDepth()
    return SnailfishNumber(left, right, depth = depth)
}

fun plus(a: SnailfishNumber, b: SnailfishNumber) {
    a.value = a.value!! + b.value!!
}

fun firstValue(n: SnailfishNumber): SnailfishNumber =
        if (n.value == null) {
            firstValue(n.left!!)
        } else {
            n
        }

fun lastValue(n: SnailfishNumber): SnailfishNumber =
        if (n.value == null) {
            lastValue(n.right!!)
        } else {
            n
        }

fun previousSnailfishNumber(n: SnailfishNumber): SnailfishNumber? {
    if (n.parent == null) {
        return null
    }

    return if (n.parent!!.left === n) {
        previousSnailfishNumber(n.parent!!)
    } else {
        lastValue(n.parent!!.left!!)
    }
}

fun nextSnailfishNumber(n: SnailfishNumber): SnailfishNumber? {
    if (n.parent == null) {
        return null
    }
    return if (n === n.parent!!.right) {
        nextSnailfishNumber(n.parent!!)
    } else {
        firstValue(n.parent!!.right!!)
    }
}

fun explode(n: SnailfishNumber): Boolean {
    val explode = nextToExplode(n)
    return if (explode == null) {
        false
    } else {
        explodeSnailfishNumber(explode)
        true
    }
}

fun nextToExplode(n: SnailfishNumber): SnailfishNumber? {
    if (n.value != null) {
        return null
    }
    if (n.depth == 4) {
        return n
    }
    return nextToExplode(n.left!!) ?: nextToExplode(n.right!!)
}

fun explodeSnailfishNumber(n: SnailfishNumber) {
    val (left, right, _, depth, parent) = n
    val previous = previousSnailfishNumber(n)
    if (previous != null) {
        plus(previous, left!!)
    }

    val next = nextSnailfishNumber(n)
    if (next != null) {
        plus(next, right!!)
    }

    if (parent!!.left == n) {
        parent.left = SnailfishNumber(parent = parent, value = 0, depth = depth)
    } else {
        parent.right = SnailfishNumber(parent = parent, value = 0, depth = depth)
    }
}

fun split(n: SnailfishNumber): Boolean {
    val split = nextToSplit(n)
    return if (split == null) {
        false
    } else {
        splitSnailfishNumber(split)
        true
    }
}

fun nextToSplit(n: SnailfishNumber): SnailfishNumber? {
    if (n.value != null && n.value!! > 9) {
        return n
    }

    val left = n.left?.let { nextToSplit(it) }
    if (left?.value != null) {
        return left
    }

    val right = n.right?.let { nextToSplit(it) }
    if (right?.value != null) {
        return right
    }

    return null
}

fun splitSnailfishNumber(n: SnailfishNumber) {
    val left = floor((n.value!! / 2.0)).toInt()
    val right = ceil((n.value!! / 2.0)).toInt()
    val split = SnailfishNumber(parent = n.parent,
            depth = n.depth,
            left = SnailfishNumber(value = left, depth = n.depth + 1),
            right = SnailfishNumber(value = right, depth = n.depth + 1))
    if (n.parent!!.left == n) {
        n.parent!!.left = split
    } else {
        n.parent!!.right = split
    }
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

    fun magnitude(): Int {
        return if (value != null) {
            value!!
        } else {
            3 * left!!.magnitude() + 2 * right!!.magnitude()
        }
    }

    override fun toString(): String =
            if (value != null) {
                value.toString()
            } else {
                "[${left.toString()}, ${right.toString()}]"
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