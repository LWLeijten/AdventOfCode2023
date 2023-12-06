package utils

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
