import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.exp

/**
 * Splits a list into head and tail components
 */
fun <T> List<T>.split() = first() to drop(1)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/inputs/$name.txt").readLines()

fun readInputAsString(name: String) = Path("src/inputs/$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

inline fun <T> checkEqual(
    expected: T,
    actual: T,
    message: (exp: T, act: T) -> String = { exp, act -> "Expected: $exp but got $act" }
) {
    check(expected == actual) { message(expected, actual) }
}

fun <T> Collection<T>.alsoPrintLines(): Collection<T> {
    println(joinToString("\n"))
    return this
}

fun <T> T.alsoPrint() = also { this.println() }