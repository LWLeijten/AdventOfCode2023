package day06

import utils.WHITESPACE_REGEX
import utils.foldListOfLongs
import utils.parseToListOfLongs
import utils.readInputAsStringLines

fun day6() {
    val fileContent = readInputAsStringLines("day06")
    val times = parseToListOfLongs(fileContent[0])
    val distances =  parseToListOfLongs(fileContent[1])
    val races = mutableListOf<Race>();
    times.indices.forEach { races.add(Race(times[it], distances[it])) }
    day6a(races)
    day6b(Race(foldListOfLongs(times), foldListOfLongs(distances)))
}

private fun day6a(races: List<Race>) {
    val result = races
        .map { r -> amountOfWins(r) }
        .filter { r -> r > 0 }
        .reduce(Long::times)
    println("Part one solution: $result")
}

private fun day6b(race: Race) {
    val result = amountOfWins(race)
    println("Part two solution: $result")
}

private fun amountOfWins(race: Race): Long {
    var wins = 0L
    for (i in 0..race.time) {
        val distance = i * (race.time - i)
        if (distance > race.distance) {
            wins++
        }
    }
    return wins
}