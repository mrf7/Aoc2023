fun main() {
    fun parseInput(input: String) = input.split("\n\n").map { it.split("\n") }

    fun part1(input: String): Int {
        val maps = parseInput(input)
        val map = maps.first()
        (1..<map.size).first {
            val above = map.slice(0..it).alsoPrintLines()
            val below = map.slice(it + 1..<map.size).alsoPrintLines()
            false
        }
        return maps.size
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
