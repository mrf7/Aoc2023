package twentytwo

import println
import readInput

fun main() {
    val throwScores = mapOf("R" to 1, "P" to 2, "S" to 3)
    val translate = mapOf("X" to "R", "A" to "R", "Y" to "P", "B" to "P", "Z" to "S", "C" to "S")
    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val (opp, me) = game.split(" ").map {
                translate[it] ?: error("didnt find $it")
            }
            val winScore =
                if (opp == me) 3 else if ((opp == "R" && me == "P") || (opp == "P" && me == "S") || opp == "S" && me == "R") 6 else 0
            winScore + throwScores[me]!!
        }
    }

    val winScore = mapOf("X" to 0, "Y" to 3, "Z" to 6)
    fun getShape(opp: String, result: String): String {
        return if (result == "Y") {
            opp
        } else if (result == "X") {
            when (opp) {
                "R" -> "S"
                "P" -> "R"
                "S" -> "P"
                else -> error("Other condition")
            }
        } else {
            when (opp) {
                "R" -> "P"
                "P" -> "S"
                "S" -> "R"
                else -> error("Other condition")
            }
        }
    }

    fun part2(input: List<String>): Int {
        val shapes = listOf("R", "P", "S")
        return input.map { it.replace("[ABC]".toRegex()) { translate[it.value]!! } }.sumOf { game ->
            val (opp, result) = game.split(" ")
            val myThrow = getShape(opp, result)
            winScore[result]!! + throwScores[myThrow]!!
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02-22_test")
    check(part1(testInput) == 15)

    check(part2(testInput) == 12)

    val input = readInput("Day02-22")
    part1(input).println()
    part2(input).println()
}
