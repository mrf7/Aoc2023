fun main() {
    fun extrapolateNext(numbers: List<Int>): Int {
        val diffs = numbers.windowed(2) { (first, second) -> second - first }//.alsoPrint()
        return if (diffs.all { it == 0 }) {
            numbers.last()
        } else {
            numbers.last() + extrapolateNext(diffs)
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { extrapolateNext(it.split(" ").map(String::toInt)) }//.alsoPrint()
    }

    fun extrapolatePrevious(numbers: List<Int>): Int {
        val diffs = numbers.windowed(2) { (first, second) -> second - first }//.alsoPrint()
        return if (diffs.all { it == 0 }) {
            numbers.first()
        } else {
            numbers.first() - extrapolatePrevious(diffs)
        }
    }
    fun part2(input: List<String>): Int {
        return input.sumOf { extrapolatePrevious(it.split(" ").map(String::toInt)) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    checkEqual(114, part1(testInput))
    checkEqual(2, part2(testInput))

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
