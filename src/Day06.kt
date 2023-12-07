fun main() {
    fun parseInput(list: List<String>): List<Pair<Int, Int>> {
        val (time, distance) = list.map { it.substringAfter(": ").trim().split("\\s+".toRegex()).map(String::toInt) }
        return time.zip(distance)
    }

    fun part1(input: List<String>): Int {
        val timeToDist = parseInput(input)
        return timeToDist.map { (time, record) ->
            (1..<time).fold(0) { acc, holdTime ->
                val distance = holdTime * (time - holdTime)
                if (distance > record) acc + 1 else acc
            }
        }.fold(1) { acc, current -> acc * current }
    }

    fun part2(input: List<String>): Int {
        val (time, record) = input.map { it.substringAfter(": ").replace(" ","").trim().toLong() }
        return (1..<time).fold(0) { acc, holdTime ->
            val distance = holdTime * (time - holdTime)
            if (distance > record) acc + 1 else acc
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
