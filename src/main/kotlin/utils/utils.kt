package utils

fun readInputAsStringLines(day: String): List<String> {
    return { }::class.java.getResource("/inputs/$day.txt").readText().split("\n")
}