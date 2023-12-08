import javax.xml.crypto.dsig.keyinfo.RetrievalMethod
import kotlin.time.measureTime

private typealias DirectionMap = Map<String, Pair<String, String>>

fun main() {
    val lineMap = """([A-Z0-9]{3}) = \(([A-Z0-9]{3}), ([A-Z0-9]{3})\)""".toRegex()
    fun parseInput(input: List<String>): Pair<String, DirectionMap> {
        val (directLine, mapLines) = input.split().run { copy(first, second.dropWhile { it.isBlank() }) }
        val map = mapLines
            .map { lineMap.matchEntire(it)?.groupValues ?: error("didnt match the line $it") }
            .associate { (_, dest, left, right) -> dest to Pair(left, right) }
        return directLine to map
    }

    fun getFirstEnd(
        start: String,
        directLine: String,
        map: DirectionMap,
        endPredicate: (String) -> Boolean
    ): Pair<Int, String> {
        var curr = start
        var count = 0
        while (count == 0 || !endPredicate(curr)) {
            val mapEntry = map[curr] ?: error("didnt find $curr")
            val direction = directLine[count % directLine.length]
            curr =
                if (direction == 'L') mapEntry.first else if (direction == 'R') mapEntry.second else error("wrong direction $direction")

            count += 1
        }
        return count to curr
    }

    fun part1(input: List<String>): Int {
        val (directLine, map) = parseInput(input)

//        var curr = "AAA"
//        var count = 0
//        while (curr != "ZZZ") {
//            val mapEntry = map[curr] ?: error("didnt find $curr")
//            val direction = directLine[count % directLine.length]
//            curr =
//                if (direction == 'L') mapEntry.first else if (direction == 'R') mapEntry.second else error("wrong direction $direction")
//
//            count += 1
//        }
        return getFirstEnd("AAA", directLine, map) { it == "ZZZ" }.first
    }

    fun part2(input: List<String>): Int {
        val (directLine, map) = parseInput(input)
        val startingPoints = map.filterKeys { it.endsWith("A") }.keys

        var currentLocations = startingPoints.toList()
        val shortestsRoutes =
            currentLocations.map { start -> getFirstEnd(start, directLine, map) { it.endsWith("Z") }.first }
                .also { it.println() }

//        var count = 0
//        while (!currentLocations.all { it.endsWith("Z") }) {
//            currentLocations = currentLocations.map { curr ->
//                val mapEntry = map[curr] ?: error("didnt find $curr")
//                val direction = directLine[count % directLine.length]
//                if (direction == 'L') mapEntry.first else if (direction == 'R') mapEntry.second else error("wrong direction $direction")
//            }.also { if (it.any { it.endsWith("Z") }) println("$count: ${it.filter { it.endsWith("Z") }}") }
//            count += 1
//        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 2)
//    check(part2(testInput2) == 6)

    val input = readInput("Day08")
    part1(input).println()
    measureTime {
        part2(input).println()
    }.also { it.println() }
}
