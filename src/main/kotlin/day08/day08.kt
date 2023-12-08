package day08

import utils.readInputAsStringLines

fun day8() {
    val fileContent = readInputAsStringLines("day08")
    val commands = fileContent[0]
    val nodes = fileContent.subList(2, fileContent.size)
    val network = Network(nodes)
    val partOne = network.traverse(commands)
    println("Part one solution: $partOne")
    val partTwo = network.traverseLikeGhosts(commands)
    println("Part two solution: $partTwo")
}