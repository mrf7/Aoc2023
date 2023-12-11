fun main() {
    data class Coord(val x: Int, val y: Int) {
        fun N() = Coord(x, y - 1).takeIf { it.x >= 0 || it.y >= 0 }
        fun S() = Coord(x, y + 1).takeIf { it.x >= 0 || it.y >= 0 }
        fun E() = Coord(x + 1, y).takeIf { it.x >= 0 || it.y >= 0 }
        fun W() = Coord(x - 1, y).takeIf { it.x >= 0 || it.y >= 0 }
    }

    fun List<String>.pipeAt(coordinate: Coord?) = coordinate?.let { this.getOrNull(it.y)?.getOrNull(it.x) }
    fun connectingPipes(input: List<String>, coordinate: Coord): Pair<Coord, Coord> {
        val (x, y) = coordinate
//                              (x to y - 1),
//            (x - 1 to y),                   (x + 1 to y),
//                            (x to y + 1),

        val connecting = mutableListOf<Coord>()
        coordinate.N()?.apply {
            when (input.pipeAt(this)) {
                '|', '7', 'F' -> connecting.add(this)
            }
        }
        coordinate.S()?.apply {
            when (input.pipeAt(this)) {
                '|', 'J', 'L' -> connecting.add(this)
            }
        }
        coordinate.E()?.apply {
            when (input.pipeAt(this)) {
                '-', 'L', 'F' -> connecting.add(this)
            }
        }
        coordinate.W()?.apply {
            when (input.pipeAt(this)) {
                '-', '7', 'J' -> connecting.add(this)
            }
        }
        return connecting.takeIf { it.size == 2 }?.let { it[0] to it[1] }
            ?: error("Got more than 2 connections $connecting")
    }

    fun getNext(input: List<String>, a: Coord, prevA: Coord): Coord {
        return when (input.pipeAt(a)) {
            '|' -> if (prevA == a.N()) a.S() else a.N()
            '-' -> if (prevA == a.E()) a.W() else a.E()
            'L' -> if (prevA == a.N()) a.E() else a.N()
            'J' -> if (prevA == a.N()) a.W() else a.N()
            '7' -> if (prevA == a.S()) a.W() else a.S()
            'F' -> if (prevA == a.S()) a.E() else a.S()
            else -> error("Invalid pipe at $a: ${input.pipeAt(a)}")
        } ?: error("walked out of bounds from $a, pipe:${input.pipeAt(a)}")
    }

    fun part1(input: List<String>): Int {
        val startCoord =
            input.indexOfFirst { it.contains("S") }.let { Coord(input[it].indexOf('S'), it) }
        var (prevA, prevB) = startCoord to startCoord
        var (a, b) = connectingPipes(input, startCoord).alsoPrint()
        var steps = 1
        while (a != b && prevA != b && prevB != a) {
            val (tempA, tempB) = a to b
            a = getNext(input, a, prevA)
            b = getNext(input, b, prevB)
            prevA = tempA
            prevB = tempB

            steps++
        }
        return steps
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    checkEqual(4, part1(testInput))

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
