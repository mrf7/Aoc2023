import kotlin.math.min

fun main() {
    fun parseInput(input: String) = input.split("\n\n").map { it.split("\n") }

    fun findHorizontalMirror(map: List<String>) = (0..<map.size - 1).firstOrNull {
        val above = map.slice(0..it)
        val below = map.slice(it + 1..<map.size)
        val checkSize = min(above.size, below.size)
        val match = above.takeLast(checkSize).reversed() == below.take(checkSize)
        match
    }?.let { it + 1 } ?: 0

    fun findVerticalMirror(map: List<String>) = (0..<map.first().length - 1).firstOrNull { index ->
        println("slicing at $index")
        val left = map.map { it.take(index + 1) }
        val right = map.map { it.drop(index + 1) }
        left.zip(right).map { (l, r) -> "$l|$r" }.alsoPrintLines()
        val checkSize = min(left.first().length, right.first().length)
        val checkLeft = left.map { it.takeLast(checkSize) }
        val checkRight = right.map { it.take(checkSize).reversed() }
        println(checkRight == checkLeft)

        println("\n" + "*".repeat(30) + "\n")
        checkLeft == checkRight
    }?.let { it + 1 } ?: 0

    fun part1(input: String): Int {
        val maps = parseInput(input)
        return maps.map { map ->
            (findHorizontalMirror(map) * 100) + findVerticalMirror(map)
        }.alsoPrint().sum()
    }

    fun findHorizontalMirrorSmudge(map: List<String>) = (0..<map.size - 1).firstOrNull {
        val above = map.slice(0..it)
        val below = map.slice(it + 1..<map.size)
        val checkSize = min(above.size, below.size)
        val pairs =
            above.takeLast(checkSize).reversed().zip(below.take(checkSize)).filter { (above, below) -> above != below }
        val smudgedPair = pairs.singleOrNull()
            ?.takeIf { (above, below) -> above.filterIndexed { index, c -> c != below[index] }.length == 1 }
        smudgedPair != null
    }?.let { it + 1 } ?: 0

    fun findVerticalMirrorSmudge(map: List<String>) = (0..<map.first().length - 1).firstOrNull { index ->
        println("slicing at $index")
        val left = map.map { it.take(index + 1) }
        val right = map.map { it.drop(index + 1) }
        left.zip(right).map { (l, r) -> "$l|$r" }.alsoPrintLines()
        val checkSize = min(left.first().length, right.first().length)
        val checkLeft = left.map { it.takeLast(checkSize) }
        val checkRight = right.map { it.take(checkSize).reversed() }
        val badPair =
            checkLeft.zip(checkRight).singleOrNull { (left, right) -> left != right } ?: return@firstOrNull false
        badPair.first.filterIndexed { i, c -> c != badPair.second[i] }.singleOrNull() != null
    }?.let { it + 1 } ?: 0

    fun part2(input: String): Int {
        val maps = parseInput(input)

        return maps.map { map ->
            (findHorizontalMirrorSmudge(map) * 100) + findVerticalMirrorSmudge(map)
        }.alsoPrint().sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day13_test")
    checkEqual(405, part1(testInput))
    checkEqual(400, part2(testInput))

    val input = readInputAsString("Day13")
    part1(input).println()
    part2(input).println()
}
