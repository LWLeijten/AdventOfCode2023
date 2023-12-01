package day01

import kotlin.text.StringBuilder


val NUMBER_MAPPING = mapOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

fun day1() {
    val fileContent = { }::class.java.getResource("/inputs/day01.txt").readText().split("\n")
    day1a(fileContent)
    day1b(fileContent)
}

private fun day1a(fileContent: List<String>) {
    val result = fileContent
        .map { s -> s.filter { c -> c.isDigit() } }
        .sumOf { s -> String(charArrayOf(s.first(), s.last())).toInt() }
    println("Part one solution: $result")
}

private fun day1b(fileContent: List<String>) {
    val result = fileContent
        .map { fc -> preprocess(fc) }
        .sumOf { s -> String(charArrayOf(s.first(), s.last())).toInt() }
    println("Part two solution: $result")
}

private fun preprocess(fc: String): MutableList<Char> {
    val newList = mutableListOf<Char>()
    val sb: StringBuilder = StringBuilder()
    for (character in fc) {
        sb.append(character)
        if (character.isDigit()) {
            newList.add(character)
        } else {
            if (NUMBER_MAPPING.keys.any { k -> sb.endsWith(k) }) {
                newList.add(NUMBER_MAPPING[NUMBER_MAPPING.keys.first { k -> sb.endsWith(k) }]!!)
            }
        }
    }
    return newList
}
