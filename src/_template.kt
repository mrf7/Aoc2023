fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    checkEqual(1, part1(testInput))

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
