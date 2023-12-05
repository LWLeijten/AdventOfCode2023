package day05

import utils.WHITESPACE_REGEX
import utils.readInputAsStringLines

fun day5() {
    val fileContent = readInputAsStringLines("day05")
    val seeds = fileContent[0]
        .substringAfter(":")
        .trim()
        .split(WHITESPACE_REGEX)
        .map { s -> s.toLong() }
    val stsRanges = initRange(fileContent, "seed-to-soil map:")
    val stfRanges = initRange(fileContent, "soil-to-fertilizer map:")
    val ftwRanges = initRange(fileContent, "fertilizer-to-water map:")
    val wtlRanges = initRange(fileContent, "water-to-light map:")
    val lttRanges = initRange(fileContent, "light-to-temperature map:")
    val tthRanges = initRange(fileContent, "temperature-to-humidity map:")
    val htlRanges = initRange(fileContent, "humidity-to-location map:")
    val results = mutableListOf<Long>()
    // Christmas tree
    for (seed in seeds) {
        results.add(
            calculateNextValue(
                calculateNextValue(
                    calculateNextValue(
                        calculateNextValue(
                            calculateNextValue(
                                calculateNextValue(
                                    calculateNextValue(
                                        seed,
                                        stsRanges
                                    ), stfRanges
                                ), ftwRanges
                            ), wtlRanges
                        ), lttRanges
                    ), tthRanges
                ), htlRanges
            )
        )
    }
    println(results.min())
}

private fun calculateNextValue(input: Long, ranges: MutableList<Range>): Long {
    for (range in ranges) {
        if (input >= range.sourceStart && input <= range.sourceStart + range.length) {
            return range.destinationStart + (input - range.sourceStart)
        }
    }
    return input
}

private fun initRange(fileContent: List<String>, mapHeader: String): MutableList<Range> {
    var index = fileContent.indexOf(mapHeader) + 1
    val ranges = mutableListOf<Range>()
    while (true) {
        if (index >= fileContent.size || fileContent[index] == "") {
            break
        }
        val split = fileContent[index]
            .split(WHITESPACE_REGEX)
            .map { s -> s.toLong() }
        ranges.add(Range(split[0], split[1], split[2]))
        index++
    }
    return ranges
}