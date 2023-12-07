package day07

import utils.readInputAsStringLines

val CARD_MAP = mapOf('T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)
val CARD_MAP_B = mapOf('T' to 10, 'J' to 1, 'Q' to 12, 'K' to 13, 'A' to 14)

fun day7() {
    val fileContent = readInputAsStringLines("day07")
    val handBidsOne = initHandBids(fileContent, CARD_MAP, ::mapHandToGroupSizes)
    val handBidsTwo = initHandBids(fileContent, CARD_MAP_B, ::mapHandToGroupSizesTwo)
    val partOne = solve(handBidsOne)
    println("Part one solution: $partOne")
    val partTwo = solve(handBidsTwo)
    println("Part two solution: $partTwo")
}

private fun solve(handBids: List<HandBid>): Int {
    return handBids.sortedWith(
        compareBy({ it.cardGroups[0] },
            { it.cardGroups.getOrElse(1) { 0 } },
            { it.hand[0] },
            { it.hand[1] },
            { it.hand[2] },
            { it.hand[3] },
            { it.hand[4] })
    ).mapIndexed { i, hb -> (i + 1) * hb.bid }.sum()
}

private fun initHandBids(
    fileContent: List<String>,
    cardMapping: Map<Char, Int>,
    grouping: (List<Int>) -> List<Int>
): List<HandBid> {
    val handBids = mutableListOf<HandBid>()
    fileContent.forEach { fc ->
        val hand = fc.split(" ")[0].map { mapCharToCardNumber(it, cardMapping) }
        handBids.add(
            HandBid(
                hand,
                grouping(hand),
                fc.split(" ")[1].toInt()
            )
        )
    }
    return handBids
}

private fun mapCharToCardNumber(char: Char, cardMapping: Map<Char, Int>): Int {
    return if (char.isDigit()) {
        char.digitToInt()
    } else {
        cardMapping[char]!!
    }
}

private fun mapHandToGroupSizes(hand: List<Int>): List<Int> {
    return hand
        .groupBy { it }
        .map { it.value.size }
        .sortedByDescending { it }
}

private fun mapHandToGroupSizesTwo(hand: List<Int>): List<Int> {
    // method could be cleaner, but it works ¯\_(ツ)_/¯
    val grouped = hand.groupBy { it }
    var jokers = 0
    // Calculate amount of jokers + edge case for 5 jokers
    if (grouped.containsKey(1)) {
        jokers = grouped[1]!!.size
        if (jokers == 5) {
            return arrayListOf(5)
        }
    }
    // filter out jokers
    val filtered = grouped.filter { it.key != 1 }.toMutableMap()
    // get the card we should add the jokers to
    val subst = filtered.toSortedMap(compareByDescending { filtered[it]?.size }).firstKey()
    // add jokers to the determined card
    for (i in 0..<jokers) {
        val copy = filtered[subst]?.toMutableList()!!
        copy.add(subst)
        filtered[subst] = copy
    }
    return filtered.map { it.value.size }.sortedByDescending { it }
}
