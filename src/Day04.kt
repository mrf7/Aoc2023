import kotlin.math.pow
import kotlin.time.measureTime

fun main() {
    fun String.parseGame() = substringAfter(": ", "").split("|").map(String::trim)
        .map { it.split(" +".toRegex()).map(String::toInt) }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (winning, mine) = it.parseGame()
            val matches = winning intersect mine
            matches.size.takeIf { it > 0 }?.let { (2.0).pow(matches.size - 1).toInt() } ?: 0
        }
    }

    fun part2(input: List<String>, copy: Boolean = false): Int {
        val current = input.firstOrNull() ?: return 0
        val (winning, mine) = current.parseGame()
        val matches = (winning intersect mine).size
        return if (matches == 0) {
            1 + if (copy) 0 else part2(input.drop(1))
        } else {
            (1..matches).sumOf {
                part2(input.drop(it), true)
            } + if (copy) 1 else part2(input.drop(1)) + 1
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val result = part2(testInput)
    check(result == 30)

    val input = readInput("Day04")
    part1(input).println()
    measureTime {
        part2(input).println()
    }.also { println(it) }
}
