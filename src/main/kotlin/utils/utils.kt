package utils

import kotlin.math.abs

val WHITESPACE_REGEX = "\\s+".toRegex()

fun readInputAsStringLines(day: String): List<String> {
    return { }::class.java.getResource("/inputs/$day.txt").readText().split("\n").map { s -> s.trim() }
}

fun parseToListOfLongs(string: String): List<Long> {
    return string
        .substringAfter(":")
        .trim()
        .split(WHITESPACE_REGEX)
        .map { s -> s.toLong() }
}

fun foldListOfLongs(list: List<Long>) =
    list.map { x -> x.toString() }.fold("") { acc, next -> acc + next }.toLong()

fun gcd(a:Long, b:Long): Long {
    var x = a
    var y = b
    while (y != 0L) {
        val temp = y
        y = x % y
        x = temp
    }
    return x
}

fun lcm(a:Long, b:Long): Long {
    return a * (b/gcd(a,b))
}

fun lcm(list: List<Long>): Long {
    return list.reduce(::lcm)
}

fun manhattanDistance(a: Pair<Int,Int>, b: Pair<Int,Int>) : Int {
    return abs(a.first - b.first) + abs(a.second - b.second)
}
