package day10

import java.io.File

private val input = File("src/day10/input.txt").readLines()

fun main() {
    val brackets = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val errors = input.map { line ->
        val lastOpenings = mutableListOf<Char>()
        var error = false
        line.firstOrNull { character ->
            when (character) {
                in brackets.keys -> lastOpenings.add(character)
                in brackets.values -> error = character != brackets[lastOpenings.removeLast()]
            }
            error
        }
    }.sumBy { char ->
        when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }
    println(errors)
}