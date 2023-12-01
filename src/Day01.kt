fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            "${line.first { it.isDigit() }}${line.last { it.isDigit() }}"
        }.sumOf { it.toInt() }
    }

    val numTable = mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (_, firstString) = it.findAnyOf(numTable.keys + "0123456789".toCharArray().map(Char::toString))
                ?: error("Didnt find key in $it")
            val (_, lastString) = it.findLastAnyOf(numTable.keys + "0123456789".toCharArray().map(Char::toString))
                ?: error("Didnt find key in $it")
            ((firstString.toIntOrNull() ?: numTable[firstString]!!) * 10) + (lastString.toIntOrNull()
                ?: numTable[lastString]!!)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
