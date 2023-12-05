import kotlin.math.pow

fun main() {
    fun part1(input: List<String>) : Int {
        return input.sumOf { line ->
            val ticketValues = line.split(":").last()
            val winningValues = ticketValues.split('|').first().split(" ").map { it.trim() }.mapNotNull { if (it.isNullOrEmpty()) null else it.toInt() }
            val actualValues = ticketValues.split('|').last().split(" ").map { it.trim() }.mapNotNull { if (it.isNullOrEmpty()) null else it.toInt() }
            val numberOfWinning = actualValues.sumOf {
                if (it in winningValues) 1L else 0L
            }
            2.0.pow((numberOfWinning - 1).toDouble()).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val winningValuesList = input.map { line ->
            val ticketValues = line.split(":").last()
            ticketValues.split('|').first().split(" ").map { it.trim() }
                .mapNotNull { if (it.isNullOrEmpty()) null else it.toInt() }
        }

        val actualValuesList = input.map { line ->
            val ticketValues = line.split(":").last()
            ticketValues.split('|').last().split(" ").map { it.trim() }
                .mapNotNull { if (it.isNullOrEmpty()) null else it.toInt() }
        }

        val ticketCountMap: List<Int> = List(input.size) { 1 }

        return getScratchTickets(0, winningValuesList, actualValuesList, ticketCountMap)
    }

//    fun part2(input: List<String>): Int =
    val currentDay = "4"
    val finalInput = readInput("day$currentDay/Final")
    // part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    check(part1(part1TestInput) == 13)

    part1(finalInput).println()

    // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    check(part2(part2TestInput) == 30)
    println(part2(part2TestInput))
    println(part2(finalInput))
}

private fun getScratchTickets(currentTicket: Int, winningValuesList: List<List<Int>>, actualValueList: List<List<Int>>, ticketMap: List<Int>) : Int {
    if (currentTicket == ticketMap.size) return ticketMap.sumOf { it }
    val winningValues = winningValuesList[currentTicket]
    val actualValues = actualValueList[currentTicket]

    val numberOfWinning = actualValues.sumOf { if (it in winningValues) 1L else 0L }.toInt()
    val copies = ticketMap[currentTicket]

    val newTicketMap = ticketMap.mapIndexed { i, it ->
        if (i in currentTicket+1..<currentTicket+numberOfWinning+1) {
            it+copies
        } else it
    }
    return getScratchTickets(currentTicket+1,  winningValuesList, actualValueList, newTicketMap)
}