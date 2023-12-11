fun main() {
    fun part1(input: List<String>) : Long =
        input.sumOf { row ->
            val data = row.split(' ').map { it.trim().toLong() }
            extrapolateNextValue(data) + data.last()
        }

    fun part2(input: List<String>) : Long =
        input.sumOf { row ->
            val data = row.split(' ').map { it.trim().toLong() }
            val firstValues = extrapolatePreviousValue(data, listOf(data.first()))
            firstValues.reversed().reduce { sum, a -> a - sum }
        }

    val currentDay = "9"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    println(part1(part1TestInput))
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    part2(finalInput).println()
}

private fun extrapolateNextValue(previousRow: List<Long>) : Long {
    val currentRow = previousRow.mapIndexedNotNull { i, value ->
        if (i == 0) null
        else value - previousRow[i-1]
    }
    if (currentRow.count {it == 0L} == currentRow.size) return 0

    return currentRow.last() + extrapolateNextValue(currentRow)
}

private fun extrapolatePreviousValue(previousRow: List<Long>, firstValues: List<Long>) : List<Long> {
    val currentRow = previousRow.mapIndexedNotNull { i, value ->
        if (i == 0) null
        else value - previousRow[i-1]
    }

    if (currentRow.count {it == 0L} == currentRow.size) return firstValues + 0L

    return extrapolatePreviousValue(currentRow, firstValues+currentRow.first())
}