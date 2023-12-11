import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun List<String>.expandSpacetime(): List<String> {
        val rowsExpanded = flatMap { line ->
            if (line.all { it == '.' }) listOf(line, line) else listOf(line)
        }
        val emptyCols = (0..<rowsExpanded.first().length).filter { col ->
            rowsExpanded.all { it[col] == '.' }
        }
        val exp = rowsExpanded.map { line ->
            buildString {
                append(line)
                emptyCols.forEachIndexed { index, i ->
                    insert(index + i, ".")
                }
            }
        }
        return exp
    }

    fun List<String>.emptyRowsCols(): Pair<List<Int>, List<Int>> {
        val emptyRows = mapIndexedNotNull { index, line ->
            if (line.all { it == '.' }) index else null
        }
        val emptyCols = (0..<first().length).filter { col ->
            all { it[col] == '.' }
        }
        return emptyRows to emptyCols
    }

    fun <T> List<T>.combinations(): List<Pair<T, T>> {
        return sequence {
            this@combinations.forEachIndexed { index, current ->
                this@combinations.subList(index + 1, this@combinations.size).map {
                    yield(current to it)
                }
            }
        }.toList()
    }

    data class Galaxy(val num: Int, val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val expanded = input.expandSpacetime()
        val galaxies = expanded.flatMapIndexed { row: Int, line: String ->
            line.mapIndexedNotNull { index, c -> if (c == '#' || c.isDigit()) index else null }
                .map { col -> row to col }
        }.withIndex().map { (index, coord) -> Galaxy(index + 1, coord.first, coord.second) }

        val galaxyPairs = galaxies.combinations()
        val paths = galaxyPairs.map { (first, second) ->
            Pair(first.num, second.num) to abs(first.x - second.x) + abs(first.y - second.y)
        }
        return paths.sumOf { it.second }
    }

    fun numsBetween(first: Int, second: Int):IntRange = min(first, second)..max(first,second)

    fun part2(input: List<String>, expandSize: Int = 1_000_000): Long {
        val galaxies = input.flatMapIndexed { row: Int, line: String ->
            line.mapIndexedNotNull { index, c -> if (c == '#' || c.isDigit()) index else null }
                .map { col -> row to col }
        }.withIndex().map { (index, coord) -> Galaxy(index + 1, coord.first, coord.second) }
        val (emptyRows, emptyCols) = input.emptyRowsCols()

        val galaxyPairs = galaxies.combinations()
        val paths = galaxyPairs.map { (first, second) ->
            val xTraverse = numsBetween(first.x, second.x)
            val yTraverse = numsBetween(first.y, second.y)
            val emptyX = xTraverse.count { it in emptyRows }
            val emptyY = yTraverse.count { it in emptyCols }
            Pair(
                first.num,
                second.num
            ) to abs(first.x - second.x).toLong() + abs(first.y - second.y) + ((emptyX + emptyY) * (expandSize - 1)).toLong()
        }
        return paths.sumOf { it.second }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    checkEqual(374, part1(testInput))
    checkEqual(1030, part2(testInput, 10))
    checkEqual(8410, part2(testInput, 100))
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
