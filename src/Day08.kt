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
        startIndex: Int = 0,
        endPredicate: (String) -> Boolean
    ): Pair<Int, String> {
        var curr = start
        var count = 0
        while (count == 0 || !endPredicate(curr)) {
            val mapEntry = map[curr] ?: error("didnt find $curr")
            val direction = directLine[(count + startIndex) % directLine.length]
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

    fun part2(input: List<String>): Long {
        val (directLine, map) = parseInput(input)
        val startingPoints = map.filterKeys { it.endsWith("A") }.keys

        var currentLocations = startingPoints.toList()
        val shortestsRoutes = currentLocations
            .map { start -> getFirstEnd(start, directLine, map) { it.endsWith("Z") } }
            .also { it.println() }
        // It just so happens that for each start A and its end Z, the path from A -> Z == path from Z -> Z 😒
        // So the values of these two lists are the same. Therefore, if you go a multiple of the shortest path from a start you
        // end up on the same end. This means the answer is the LCM of all the shortest routes. Probably a way to do it
        // with an offset if this wasn't true but idc to figure it out
        val routeToLoop = shortestsRoutes
            .map { (len, end) -> getFirstEnd(end, directLine, map, len) { it == end } }
            .also { it.println() }
        println(directLine.length)

        return shortestsRoutes.map { it.first.toLong() }.findLCM()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 2)
    check(part2(testInput2) == 6L)
    val input = readInput("Day08")
    part1(input).println()
    measureTime {
        part2(input).println()
    }.also { it.println() }
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun List<Long>.findLCM(): Long {
    var result = this[0]
    for (i in 1 until size) {
        result = findLCM(result, this[i])
    }
    return result
}