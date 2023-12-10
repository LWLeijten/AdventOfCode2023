package day09

import utils.WHITESPACE_REGEX
import utils.readInputAsStringLines

fun day9() {
    val fileContent = readInputAsStringLines("day09")
    val result = day9a(fileContent)
    val result2 = day9b(fileContent)
    println("Part one solution: $result")
    println("Part two solution: $result2")
}

private fun day9a(fileContent: List<String>): Int {
    return fileContent
        .map {
            it.split(WHITESPACE_REGEX)
                .map { it.toInt() }
        }.sumOf { solveReportLine(it) }
}

private fun day9b(fileContent: List<String>): Int {
    return fileContent
        .map {
            it.split(WHITESPACE_REGEX)
                .map { it.toInt() }
        }.sumOf { solveReportLineBackwards(it) }
}


private fun solveReportLine(input: List<Int>): Int {
    val lines = expandLines(input)
    lines.last().add(0)
    for (i in lines.size - 1 downTo 1) {
        lines[i - 1].add(lines[i].last() + lines[i - 1].last())
    }
    return lines[0].last()
}


private fun solveReportLineBackwards(input: List<Int>): Int {
    val lines = expandLines(input)
    lines.last().add(0, 0)
    for (i in lines.size - 1 downTo 1) {
        lines[i - 1].add(0, lines[i - 1].first() - lines[i].first())
    }
    return lines[0].first()
}

private fun expandLines(line: List<Int>): MutableList<MutableList<Int>> {
    val lines = mutableListOf(line.toMutableList())
    var currentLine = lines[0]
    while (!currentLine.all { it == 0 }) {
        val newLine = mutableListOf<Int>()
        for (i in 0..<currentLine.size - 1) {
            newLine.add(currentLine[i + 1] - currentLine[i])
        }
        lines.add(newLine)
        currentLine = newLine
    }
    return lines
}
