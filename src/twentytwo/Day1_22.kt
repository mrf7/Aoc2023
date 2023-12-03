package twentytwo

import println
import readInputAsString

fun main() {
    fun part1(input: String): Int {
        return input.split("\n\n")
            .maxOf { elfLoad ->
                elfLoad.split("\n").sumOf { it.trim().toInt() }
            }
    }

    fun part2(input: String): Int {
        return input.split("\n\n")
            .map { elfLoad ->
                elfLoad.split("\n").sumOf { it.trim().toInt() }
            }.sortedDescending()
            .take(3)
            .sum()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day01-22_test")
    check(part2(testInput) == 45000)

    val input = readInputAsString("Day01-22")
    part1(input).println()
    part2(input).println()
}
