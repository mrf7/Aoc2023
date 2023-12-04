fun main() {
    data class NumEntry(val num: Int, val startIndex: Int, val endIndex: Int, val row: Int)

    fun isPart(numEntry: NumEntry, input: List<String>): Boolean {
        val x = numEntry.startIndex - 1..numEntry.endIndex + 1
        val y = numEntry.row - 1..numEntry.row + 1
        for (column in x) {
            for (row in y) {
                val char = input.getOrNull(row)?.getOrNull(column) ?: continue
                if (!char.isDigit() && char != '.') return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val nums = mutableListOf<NumEntry>()
        input.forEachIndexed { row, line ->
            var currNum: Pair<Int, String>? = null
            for ((column, char) in line.withIndex()) {
                if (char.isDigit()) {
                    currNum = if (currNum == null) {
                        column to char.toString()
                    } else {
                        currNum.first to currNum.second + char
                    }
                } else {
                    currNum?.let { (start, num) ->
                        nums.add(NumEntry(num.toInt(), start, column - 1, row))
                    }
                    currNum = null
                }
            }
            if (currNum != null) nums.add(NumEntry(currNum.second.toInt(), currNum.first, line.lastIndex, row))
        }
        return nums.filter { isPart(it, input) }.also { it.joinToString(separator = "\n").println() }.sumOf { it.num }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
