package day04

import utils.WHITESPACE_REGEX
import utils.readInputAsStringLines
import kotlin.math.pow


fun day4() {
    val fileContent = readInputAsStringLines("day04")
    day4a(fileContent)
    day4b(fileContent)
}

private fun day4a(fileContent: List<String>) {
    var sum = 0
    fileContent
        .map { fc -> fc.substringAfter(':') }
        .forEach { m ->
            val matches = getAmountOfMatches(m)
            if (matches > 0) {
                sum += 2.0.pow((matches - 1)).toInt()
            }
        }
    println("Part one solution: $sum")
}

private fun day4b(fileContent: List<String>) {
    val cardCountMap = mutableMapOf<Int, Int>()
    (1..fileContent.size).forEach { cardCountMap[it] = 1}
    for (i in 1..fileContent.size) {
        val matches = getAmountOfMatches(fileContent[i - 1])
        if (matches > 0) {
            for (j in 1..matches) {
                val oldCount = cardCountMap.getOrDefault(i + j, 1)
                cardCountMap[i + j] = cardCountMap.getOrDefault(i, 1) + oldCount
            }
        }
    }
    val result = cardCountMap.values.sum()
    println("Part one solution: $result")
}

private fun getAmountOfMatches(row: String): Int {
    val split = row.split("|")
    val left = split[0].trim().split(WHITESPACE_REGEX)
    val right = split[1].trim().split(WHITESPACE_REGEX)
    return left.filter { l -> right.contains(l) }.size
}