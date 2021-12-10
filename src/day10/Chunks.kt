package day10

import java.io.File

private val input = File("src/day10/input.txt").readLines()

fun main() {
    val brackets = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val toComplete = mutableListOf<List<Char>>()
    val errors = input.map { line ->
        val lastOpenings = mutableListOf<Char>()
        var error = false
        val errorChar = line.firstOrNull { character ->
            when (character) {
                in brackets.keys -> lastOpenings.add(character)
                in brackets.values -> error = character != brackets[lastOpenings.removeLast()]
            }
            error
        }
        if (errorChar == null) {
            toComplete.add(lastOpenings)
        }
        errorChar
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
    val completeScores = toComplete.map { line ->
        line.reversed().map<Char, Long> {
            when (brackets[it]) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }.reduce { acc, v ->
            (acc * 5) + v
        }
    }
    println(completeScores.sorted()[completeScores.size/2])
}