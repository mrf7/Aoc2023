enum class HandRank {
    Five, Four, FullHouse, Three, TwoPair, Pair, High
}

val cards = "AKQJT98765432"
val cardsWithJokers = "AKQT98765432J"

data class Hand(val rank: HandRank, val hand: String, val wager: Int) {
    val cardValues = hand.map { cards.indexOf(it) }
    val jokerCardValues = hand.map { cardsWithJokers.indexOf(it) }
}

val handComparator: Comparator<in Hand> = Comparator { thisHand, other ->
    when {
        thisHand.rank != other.rank -> compareValues(thisHand.rank.ordinal, other.rank.ordinal)
        else -> {
            val indexToCompare =
                thisHand.cardValues.withIndex().first { (index, value) -> other.cardValues[index] != value }.index
            compareValues(thisHand.cardValues[indexToCompare], other.cardValues[indexToCompare])
        }
    }
}

val handComparatorJokers: Comparator<in Hand> = Comparator { thisHand, other ->
    when {
        thisHand.rank != other.rank -> compareValues(thisHand.rank.ordinal, other.rank.ordinal)
        else -> {
            val indexToCompare =
                thisHand.cardValues.withIndex().first { (index, value) -> other.cardValues[index] != value }.index
            compareValues(thisHand.jokerCardValues[indexToCompare], other.jokerCardValues[indexToCompare])
        }
    }
}

typealias GroupedHand = Map<Char, List<Char>>

fun main() {
    fun GroupedHand.isFullHouse(jokers: Int): Boolean {
        return when {
            any { it.value.size == 3 } && (any { it.value.size == 2 } || jokers == 1) -> true
            count { it.value.size == 2 } == 2 && jokers == 1 -> true
            any { it.value.size == 2 } && jokers == 2 -> true
            else -> false
        }
    }

    fun toHandWithJokers(line: String): Hand {
        val (hand, number) = line.split(" ")
        val wager = number.toIntOrNull() ?: error("problem parsing wager from hand $line")
        val (grouped, jokers) = hand.groupBy { it }.let {
            it.minus('J') to (it['J']?.size ?: 0)
        }
        val highestSet = (grouped.maxByOrNull { it.value.size }?.value?.size ?: 0) + jokers
        return when {
            highestSet == 5 -> Hand(HandRank.Five, hand, wager)
            highestSet == 4 -> Hand(HandRank.Four, hand, wager)
            grouped.isFullHouse(jokers) -> Hand(
                HandRank.FullHouse,
                hand,
                wager
            )

            highestSet == 3 -> Hand(HandRank.Three, hand, wager)
            grouped.count { it.value.size == 2 } == 2 || (grouped.any { it.value.size == 2 } && jokers == 1) -> Hand(
                HandRank.TwoPair,
                hand,
                wager
            )

            highestSet == 2 -> Hand(HandRank.Pair, hand, wager)
            else -> Hand(HandRank.High, hand, wager)
        }
    }

    fun toHand(line: String): Hand {
        val (hand, number) = line.split(" ")
        val wager = number.toIntOrNull() ?: error("problem parsing wager from hand $line")
        val grouped = hand.groupBy { it }
        return when {
            grouped.any { it.value.size == 5 } -> Hand(HandRank.Five, hand, wager)
            grouped.any { it.value.size == 4 } -> Hand(HandRank.Four, hand, wager)
            grouped.any { it.value.size == 3 } && grouped.any { it.value.size == 2 } -> Hand(
                HandRank.FullHouse,
                hand,
                wager
            )

            grouped.any { it.value.size == 3 } -> Hand(HandRank.Three, hand, wager)
            grouped.count { it.value.size == 2 } == 2 -> Hand(HandRank.TwoPair, hand, wager)
            grouped.any { it.value.size == 2 } -> Hand(HandRank.Pair, hand, wager)
            else -> Hand(HandRank.High, hand, wager)
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map(::toHand)
            .sortedWith(handComparator.reversed())
            .mapIndexed { index, hand -> (index + 1) * hand.wager }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map(::toHandWithJokers)
            .sortedWith(handComparatorJokers.reversed())
            .mapIndexed { index, hand -> (index + 1) * hand.wager }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
