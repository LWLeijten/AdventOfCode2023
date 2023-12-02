package day02

import utils.readInputAsStringLines

const val MAX_RED_CUBES = 12
const val MAX_GREEN_CUBES = 13
const val MAX_BLUE_CUBES = 14
val ID_REGEX = """Game (?<id>\d+)""".toRegex()
val RED_REGEX = """(?<count>\d+) red""".toRegex()
val GREEN_REGEX = """(?<count>\d+) green""".toRegex()
val BLUE_REGEX = """(?<count>\d+) blue""".toRegex()

fun day2() {
    val fileContent = readInputAsStringLines("day02")
    day2a(fileContent)
    day2b(fileContent)
}

private fun day2a(fileContent: List<String>) {
    var result = 0
    for (fc in fileContent) {
        var valid = true
        val id = ID_REGEX.find(fc)?.groups!!["id"]?.value!!
        for (round in fc.split(";")) {
            val red = getColourValue(round, RED_REGEX)
            val green = getColourValue(round, GREEN_REGEX)
            val blue = getColourValue(round, BLUE_REGEX)
            if (red > MAX_RED_CUBES || green > MAX_GREEN_CUBES || blue > MAX_BLUE_CUBES) {
                valid = false
                break
            }
        }
        if (valid) {
            result += id.toInt()
        }
    }
    println("Part one solution: $result")
}

private fun day2b(fileContent: List<String>) {
    var result = 0
    for (fc in fileContent) {
        val red = getColourMaxValue(fc, RED_REGEX)
        val green = getColourMaxValue(fc, GREEN_REGEX)
        val blue = getColourMaxValue(fc, BLUE_REGEX)
        result += red * green * blue
    }
    println("Part two solution: $result")
}

private fun getColourValue(round: String, regex: Regex): Int {
    var count = 0
    val match = regex.find(round)
    if (match != null) {
        count = match.groups["count"]?.value?.toInt()!!
    }
    return count
}

private fun getColourMaxValue(fc: String, regex: Regex): Int {
    return regex
        .findAll(fc)
        .map { matchResult -> matchResult.groups["count"]?.value?.toInt()!! }
        .max()
}
