package day16

import java.io.File

private val input = File("src/day16/input.txt").readText().map { it.toString() }

val binary = input.joinToString("") {
    it.toInt(16).toString(2).padStart(4, '0')
}

fun main() {
    val packets: Packet by lazy { parse(PacketString(binary)) }
    println("Part 1: ${packets.sumVersions()}")
    println("Part 2: ${packets.op()}")
}

fun parse(string: PacketString, packet: Packet? = null): Packet {
    val version = string.take(3).toInt(2)
    var current = packet
    when (val type = string.take(3).toInt(2)) {
        4 -> {
            var literalValue = ""
            do {
                val part = string.take(5)
                literalValue += part.drop(1)
            } while (part.startsWith('1'))

            val numberPacket = Packet(version, type, literalValue.toLong(2))

            if (current == null) {
                current = numberPacket
            } else {
                current.subPackets += numberPacket
            }
        }
        else -> {
            val newPacket = Packet(version, type)
            if (current != null) {
                current.subPackets += newPacket
            }
            current = newPacket
            val lengthType = string.take(1).toInt(2)
            val length = string.take(if (lengthType == 0) 15 else 11).toInt(2)
            if (lengthType == 0) {
                val readIndex = string.index
                while (string.index - readIndex < length) {
                    parse(string, current)
                }
            } else {
                repeat(length) {
                    parse(string, current)
                }
            }
        }
    }
    return current
}

data class Packet(
    val version: Int,
    val type: Int,
    val value: Long? = null,
    val subPackets: MutableList<Packet> = mutableListOf()
) {
    fun sumVersions(): Long = version + subPackets.sumOf { it.sumVersions() }
    fun op(): Long = when (type) {
        0 -> subPackets.sumOf { it.op() }
        1 -> subPackets.map { it.op() }.reduce { a, b -> a * b }
        2 -> subPackets.minOf { it.op() }
        3 -> subPackets.maxOf { it.op() }
        4 -> value!!
        5 -> subPackets.let { (a, b) -> if (a.op() > b.op()) 1 else 0 }
        6 -> subPackets.let { (a, b) -> if (a.op() < b.op()) 1 else 0 }
        7 -> subPackets.let { (a, b) -> if (a.op() == b.op()) 1 else 0 }
        else -> throw Exception()
    }
}

class PacketString(private val string: String) {
    var index = 0

    fun take(amount: Int): String {
        val s = string.substring(index, index + amount)
        index += amount

        return s
    }

    override fun toString(): String = string.substring(index)
}