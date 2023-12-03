package day03

import utils.readInputAsStringLines

fun day3() {
    val fileContent = readInputAsStringLines("day03")
    day3a(fileContent)
    day3b(fileContent)
}

fun day3a(fileContent: List<String>) {
    var sum = 0
    for (y in fileContent.indices) {
        var number = ""
        var isPartNumber = false
        for (x in 0..<fileContent[0].length) {
            if (!fileContent[y][x].isDigit()) {
                if (isPartNumber) {
                    sum += number.toInt()
                }
                number = ""
                isPartNumber = false
            } else {
                number += fileContent[y][x]
                isPartNumber = getIsPartNumber(fileContent, y, x) || isPartNumber
            }
        }
        if (isPartNumber) {
            sum += number.toInt()
        }
    }
    println("Part one solution: $sum")
}

fun day3b(fileContent: List<String>) {
    // Yuck
    var sum = 0
    val gearMap = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
    for (y in fileContent.indices) {
        var number = ""
        var isPartNumber = false
        var adjacentGears = mutableSetOf<Pair<Int, Int>>()
        for (x in 0..<fileContent[0].length) {
            if (!fileContent[y][x].isDigit()) {
                if (isPartNumber) {
                    updateGearMap(adjacentGears, gearMap, number)
                }
                number = ""
                isPartNumber = false
                adjacentGears = mutableSetOf()
            } else {
                number += fileContent[y][x]
                isPartNumber = getIsPartNumber(fileContent, y, x) || isPartNumber
                val gears = getAdjacentGears(fileContent, y, x)
                if (gears.size > 0) {
                    for (g in gears) {
                        adjacentGears.add(g)
                    }
                }
            }
        }
        if (isPartNumber) {
            updateGearMap(adjacentGears, gearMap, number)
        }
    }
    for (numbers in gearMap.values) {
        if (numbers.size == 2) {
            sum += numbers.reduce(Int::times)
        }
    }
    println("Part two solution: $sum")
}

private fun updateGearMap(
    adjacentGears: MutableSet<Pair<Int, Int>>,
    gearMap: MutableMap<Pair<Int, Int>, MutableList<Int>>,
    number: String
) {
    for (gear in adjacentGears) {
        if (gearMap.containsKey(gear)) {
            gearMap[gear]?.add(number.toInt())
        } else {
            gearMap[gear] = mutableListOf(number.toInt())
        }
    }
}

private fun getIsPartNumber(fileContent: List<String>, y: Int, x: Int): Boolean {
    for (yy in -1..1) {
        for (xx in -1..1) {
            val nextY = y + yy;
            val nextX = x + xx
            if (nextY < 0 || nextY >= fileContent.size || nextX < 0 || nextX >= fileContent[0].length) {
                continue
            }
            val neighbour = fileContent[y + yy][x + xx]
            if (!neighbour.isDigit() && neighbour != '.') {
                return true
            }
        }
    }
    return false
}

private fun getAdjacentGears(fileContent: List<String>, y: Int, x: Int): MutableList<Pair<Int, Int>> {
    val gears = mutableListOf<Pair<Int, Int>>()
    for (yy in -1..1) {
        for (xx in -1..1) {
            val nextY = y + yy;
            val nextX = x + xx
            if (nextY < 0 || nextY >= fileContent.size || nextX < 0 || nextX >= fileContent[0].length) {
                continue
            }
            if (fileContent[y + yy][x + xx] == '*') {
                gears.add(Pair(x + xx, y + yy))
            }
        }
    }
    return gears
}