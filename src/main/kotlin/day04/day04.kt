package day04

import utils.WHITESPACE_REGEX
import utils.readInputAsStringLines
import kotlin.math.pow


fun day4() {
    val fileContent = readInputAsStringLines("day04")
    day4a(fileContent)
}

private fun day4a(fileContent: List<String>) {
    var sum = 0
    fileContent
        .map { fc -> fc.substringAfter(':') }
        .forEach { m ->
            val split = m.split("|")
            val left = split[0].trim().split(WHITESPACE_REGEX)
            val right = split[1].trim().split(WHITESPACE_REGEX)
            val matches = left.filter { l -> right.contains(l) }.size
            if (matches > 0) {
                sum += 2.0.pow((matches - 1)).toInt()
            }
        }
    println("Part one solution: $sum")
}