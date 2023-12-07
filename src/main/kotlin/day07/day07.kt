package day07

import utils.readInputAsStringLines

val CARD_MAP = mapOf('T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)

fun day7() {
    val fileContent = readInputAsStringLines("day07")
    val handBids = mutableListOf<HandBid>()
    fileContent.forEach { fc ->
        val hand = fc.split(" ")[0].map { mapCharToCardNumber(it) }
        handBids.add(
            HandBid(
                hand,
                mapHandToGroupSizes(hand),
                fc.split(" ")[1].toInt()
            )
        )
    }
    day7a(handBids)
}

fun day7a(handBids: List<HandBid>) {
    val sum = handBids.sortedWith(
        compareBy({ it.cardGroups[0] },
            {it.cardGroups.getOrElse(1) {0} },
            { it.hand[0] },
            { it.hand[1] },
            { it.hand[2] },
            { it.hand[3] },
            { it.hand[4] })
    ).mapIndexed{ i, hb -> (i + 1) * hb.bid}.sum()
    println("Part one solution: $sum")
}

private fun mapCharToCardNumber(char: Char): Int {
    return if (char.isDigit()) {
        char.digitToInt()
    } else {
        CARD_MAP[char]!!
    }
}

private fun mapHandToGroupSizes(hand: List<Int>): List<Int> {
    return hand
        .groupBy { it }
        .map { it.value.size }
        .sortedByDescending { it }
}
