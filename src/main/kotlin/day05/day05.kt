package day05

import utils.WHITESPACE_REGEX
import utils.readInputAsStringLines
import kotlin.math.min

val RANGE_HEADERS = listOf(
    "seed-to-soil map:",
    "soil-to-fertilizer map:",
    "fertilizer-to-water map:",
    "water-to-light map:",
    "light-to-temperature map:",
    "temperature-to-humidity map:",
    "humidity-to-location map:"
)

fun day5() {
    val fileContent = readInputAsStringLines("day05")
    val seeds = fileContent[0]
        .substringAfter(":")
        .trim()
        .split(WHITESPACE_REGEX)
        .map { s -> s.toLong() }
    val ranges = RANGE_HEADERS.map { rh -> initRange(fileContent, rh) }
    day5a(ranges, seeds)
    day5b(ranges, seeds)
}

private fun day5a(ranges: List<MutableList<Range>>, seeds: List<Long>) {
    val results = mutableListOf<Long>()
    seeds
        .parallelStream()
        .forEach { seed ->
            var result = seed
            ranges.forEach { r -> result = calculateNextValue(result, r) }
            results.add(result)
        }
    println("Part one solution: " + results.min())
}

private fun day5b(ranges: List<MutableList<Range>>, seeds: List<Long>) {
    val results = mutableListOf<Long>()
    val seedRanges = getSeedRanges(seeds)
    println("Brute force goes brrrrrrrrrrrrr")
    seedRanges
        .parallelStream()
        .forEach { range ->
            var minValue = Long.MAX_VALUE
            for(i in range.first..range.second) {
                var result = i
                ranges.forEach { r -> result = calculateNextValue(result, r) }
                minValue = min(result, minValue)
            }
            results.add(minValue)
        }
    println("Part two solution: " + results.min())
}

private fun getSeedRanges(seeds: List<Long>): MutableList<Pair<Long, Long>> {
    val ranges = mutableListOf<Pair<Long, Long>>()
    for (i in 0..seeds.size-2 step 2) {
        ranges.add(Pair(seeds[i], seeds[i] + (seeds[i+1] - 1)))
    }
    return ranges
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