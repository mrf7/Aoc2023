import java.util.SortedSet
import kotlin.time.measureTime

enum class Field {
    SEED, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION;

    companion object {
        val ordered: List<List<Field>> = Field.entries.windowed(2)
    }

    infix fun mapTo(other: Field) = "${this.name}-TO-${other.name}"
}

fun <T> List<T>.split() = first() to drop(1)
fun Map<String, MapTable>.getOrValue(key: String, value: Long) =
    get(key)?.lookUp(value) ?: error("no lookup table for $key")

class MapTable(ranges: List<Pair<LongRange, LongRange>>) {
    private val sortedRanges: SortedSet<Pair<LongRange, LongRange>> = ranges.toSortedSet(compareBy { it.first.first })
    fun lookUp(num: Long): Long {
        for ((sourceRang, destRang) in sortedRanges) {
            if (num in sourceRang) return (num - sourceRang.first) + destRang.first
            if (sourceRang.last > num) return num
        }
        return num
    }
}

fun main() {
    fun processToMap(lines: List<String>): Pair<String, MapTable> {
        val (header, table) = lines.split()
        val title = header.uppercase().split(" ").first()
        val ranges = table.map { it.split(" ").map(String::toLong) }.map { (dest, source, length) ->
            source..<source + length to dest..<dest + length
        }
        return title to MapTable(ranges)
    }

    fun processTable(lines: List<String>): Pair<String, Map<Long, Long>> {
        val (header, table) = lines.split()
        return header.uppercase().split(" ").first() to table.map { it.split(" ").map(String::toLong) }
            .flatMap { (destination, source, length) ->
                (0..<length).map {
                    source + it to destination + it
                }
            }.toMap()
    }

    fun part1(input: String): Long {
        val (seeds, mappings) = input.split("\n\n").let {
            it.first().substringAfter(": ").trim().split(" ").map(String::toLong) to it.drop(1)
        }

        val lookups = mappings.associate { processToMap(it.lines()) }

        return seeds.minOf{
            val number = Field.ordered.fold(it) { num, (from, to) ->
                lookups.getOrValue(from mapTo to, num)
            }
            number
        }
    }

    fun part2(input: String): Long {
        val (seeds, mappings) = input.split("\n\n").let {
            it.first().substringAfter(": ").trim().split(" ").map(String::toLong) to it.drop(1)
        }

        val lookups = mappings.associate { processToMap(it.lines()) }
        val allSeeds = seeds.chunked(2).flatMap { (start, length) -> start..<start + length }
        return allSeeds.minOf {
            val number = Field.ordered.fold(it) { num, (from, to) ->
                lookups.getOrValue(from mapTo to, num)
            }
            number
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day05_test")
    check(part1(testInput) == 35.toLong())

    val input = readInputAsString("Day05")
    measureTime { part1(input).println() }.println()
    check(part2(testInput)== 46.toLong())
    measureTime { part2(input).println() }.println()

//    part2(input).println()
}
