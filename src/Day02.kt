import java.awt.Color.red
import kotlin.math.max

fun main() {
    val gameRegex = "Game (\\d*): (.*)".toRegex()
    fun part1(input: List<String>): Int {
        val maxes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return input.sumOf { line ->
            val (_, game, revealed) = gameRegex.find(line)?.groupValues ?: error("$line didnt match")
            val shows = revealed.split(";") // [1 red, 2 blue...]
            val valid = shows.none { sho ->
                sho.trim().split(",").any {
                    val (_, num, color) = "(\\d*) (\\w+)".toRegex().find(it.trim())?.groupValues
                        ?: error("didnt match $it")
                    (num.toInt() > maxes[color]!!)
                }
            }
            if (valid) game.toInt() else 0
        }
    }

    data class Max(val red: Int, val blue: Int, val green: Int)

    fun part2(input: List<String>): Int {
        val sum = input.sumOf { line ->
            val (_, game, revealed) = gameRegex.find(line)?.groupValues ?: error("$line didnt match")
            val shows = revealed.split(";") // [1 red, 2 blue...]
            val maxes = shows.fold(mutableMapOf<String, Int>()) { current, it ->
                it.trim().split(",").forEach {
                    val (_, num, color) = "(\\d*) (\\w+)".toRegex().find(it.trim())?.groupValues
                        ?: error("didnt match $it")
                    current[color] = max(current[color] ?: 0, num.toInt())
                }
                current
            }
            maxes.values.reduce { acc, current -> acc * current }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
//    part1(input).println()
    part2(input).println()
}
