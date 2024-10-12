import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Long {
        val cards = input.map {
            val vars = it.split(' ')
            CardScore(vars.first(), vars.last().toInt(), getValue(vars.first()))
        }
        return cards.sortedWith(cardComparator).mapIndexed { i, card ->
            (i+1) * card.score
        }.sumOf { it.toLong() }
    }

    fun part2(input: List<String>) : Long {
        val cards = input.map {
            val vars = it.split(' ')
            CardScore(vars.first(), vars.last().toInt(), 0)
        }.map { updateCard(it) }
        return cards.sortedWith(cardComparatorWithJoker).mapIndexed { i, card ->
            (i+1) * card.score
        }.sumOf { it.toLong() }
    }

    val currentDay = "7"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    println(part1(part1TestInput))
//    check(part1(part1TestInput== 288L)
    part1(finalInput).println()
//   )  // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    println(part2(part2TestInput))
    part2(finalInput).println()
}

private val strengths = "23456789TJQKA"
private val strengths2 = "J23456789TQKA"
private data class CardScore(
    val str: String,
    val score: Int,
    val value: Int,
)

private fun getValue(str: String) : Int {
    val highestOccurrence = str.groupingBy { it }.eachCount().maxBy { it.value }.value
    val setSize = str.toSet().size
    if (highestOccurrence == 5) return 7
    if (highestOccurrence == 4) return 6
    if (highestOccurrence == 3 && setSize == 2) return 5
    if (highestOccurrence == 3) return 4
    if (highestOccurrence == 2 && setSize == 3) return 3
    if (highestOccurrence == 2 && setSize == 4) return 2
    if (setSize == 5) return 1
    throw IllegalStateException("How did you get here")
}

private val cardComparator = Comparator<CardScore> { a, b ->
    if (a.value > b.value) return@Comparator 1
    if (a.value < b.value) return@Comparator -1

    return@Comparator a.str.mapIndexedNotNull { i, value ->
        val x = strengths.indexOf(a.str[i])
        val y = strengths.indexOf(b.str[i])
        if (x > y) {
            1
        } else if (x < y) {
            -1
        } else null
    }.first()
}

private fun updateCard(card: CardScore) : CardScore {
    return CardScore(
        card.str,
        card.score,
        strengths2.maxOf { getValue(card.str.replace('J', it)) },
    )
}

private val cardComparatorWithJoker = Comparator<CardScore> { a, b ->
    if (a.value > b.value) return@Comparator 1
    if (a.value < b.value) return@Comparator -1

    return@Comparator a.str.mapIndexedNotNull { i, value ->
        val x = strengths2.indexOf(a.str[i])
        val y = strengths2.indexOf(b.str[i])
        if (x > y) {
            1
        } else if (x < y) {
            -1
        } else null
    }.first()
}
