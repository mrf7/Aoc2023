import kotlin.math.min

fun main() {
    fun parseInput(input: String) = input.split("\n\n").map { it.split("\n") }

    fun findHorizontalMirrorOrNull(map: List<String>) = (0..<map.size - 1).firstOrNull {
        println("slicing at $it")
        val above = map.slice(0..it).alsoPrintLines()
        println("---------------------------")
        val below = map.slice(it + 1..<map.size).alsoPrintLines()
        val checkSize = min(above.size, below.size)
        println("Checking these")
        val match =
            above.takeLast(checkSize).alsoPrintLines().reversed().also { println("-".repeat(10)) } == below.take(
                checkSize
            ).alsoPrintLines()
        println("*".repeat(25))
        match
    }?.let { it + 1 }

    fun findVerticalMirrorOrNull(map: List<String>) = (0..<map.first().length - 1).firstOrNull { index ->
        val left = map.map { it.take(index + 1) }
        val right = map.map { it.drop(index + 1) }
        TODO()
    }

    fun part1(input: String): Int {
        val maps = parseInput(input)
        val map = maps[1]
        val mirror = findHorizontalMirrorOrNull(map)
        return ((mirror ?: 0)) * 100
    }

    fun part2(input: String): Int {
        val maps = parseInput(input)
        return maps.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day13_test")
    checkEqual(405, part1(testInput))
    // checkEqual(1, part2(testInput))

    val input = readInputAsString("Day13")
    part1(input).println()
    part2(input).println()
}
