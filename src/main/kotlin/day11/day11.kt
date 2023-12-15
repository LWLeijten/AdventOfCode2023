package day11

import utils.manhattanDistance
import utils.readInputAsStringLines
import kotlin.math.max
import kotlin.math.min

fun day11() {
    val fileContent = readInputAsStringLines("day11")
    day11a(fileContent)
    day11b(fileContent)
}

private fun day11a(fileContent: List<String>) {
    val result = solve(fileContent, 2)
    println("Part one solution: $result")
}

private fun day11b(fileContent: List<String>) {
    val result = solve(fileContent, 1000000)
    println("Part two solution: $result")
}

private fun solve(fileContent: List<String>, expansionRate: Int): Long {
    // Speed could be greatly improved by using range calculations for finding/caching empty rows
    val charLists = fileContent.map { it.toCharArray() }
    val galaxies = mutableListOf<Pair<Int, Int>>()
    for (y in charLists.indices) {
        for (x in charLists[y].indices) {
            if (charLists[y][x] == '#') {
                galaxies.add(Pair(x, y))
            }
        }
    }
    var sum = 0L
    for (x in 0..<galaxies.size - 1) {
        for (y in (x + 1)..<galaxies.size) {
            var emptyRows = 0
            if (galaxies[x].second != galaxies[y].second ) {
                emptyRows = charLists.subList(
                    min(galaxies[x].second, galaxies[y].second) + 1,
                    max(galaxies[x].second, galaxies[y].second)
                ).filter { it.all { c -> c == '.' } }.size
            }
            var emptyCols = 0
            if (galaxies[y].first != galaxies[x].first) {
                for (xx in (min(galaxies[x].first, galaxies[y].first) + 1)..<max(
                    galaxies[x].first,
                    galaxies[y].first
                )) {
                    if (charLists.map { row -> row[xx] }.all { c -> c == '.' }) {
                        emptyCols++
                    }
                }
            }
            sum += manhattanDistance(galaxies[x], galaxies[y]) + (emptyRows  + emptyCols ) * (expansionRate - 1)
        }
    }
    return sum
}
