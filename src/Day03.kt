fun main() {
    fun part1(input: List<String>): Int {
        val validNumberArray = input.mapIndexed { r, row ->
            row.mapIndexed { c, _ ->
                directions.map { direction ->
                    val adjacentRow = r + direction.first
                    val adjacentColumn = c + direction.second
                    if (adjacentRow >= 0 && adjacentRow < input.size && adjacentColumn >= 0 && adjacentColumn < row.length) {
                        input[adjacentRow][adjacentColumn] != '.' && !input[adjacentRow][adjacentColumn].isDigit()
                    } else false
                }.any { it }
            }
        }

        return input.mapIndexed { r, row ->
            val numbersInRow = row.map { if (!it.isDigit()) '.' else it }.joinToString("")
                .split('.').filter { it.isNotEmpty() }

            val numberOccurrences = numbersInRow.groupingBy { it }.eachCount()

            numberOccurrences.map {
                val occurrences = 1..it.value
                val number = it.key
                occurrences.map { occurrence ->
                    val startIndexOfNumber = findNthOccurrenceOf(row, number, occurrence)
                    val numberRange = startIndexOfNumber..<startIndexOfNumber + number.length
                    if (numberRange.map { c ->
                            validNumberArray[r][c]
                        }.any { validIndex -> validIndex }) {
                        number.toInt()
                    } else {
                        0
                    }
                }
            }.sumOf { number -> number.sumOf { it } }
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int =
        input.mapIndexed { r, row ->
            row.mapIndexed { c, _ ->
                if (row[c] == '*') {
                    getGearValue(r, c, input, 0, emptyList())
                } else 0
            }
        }.sumOf { number -> number.sumOf { it } }

//    fun part2(input: List<String>): Int =
    val currentDay = "3"
    val finalInput = readInput("day$currentDay/Final")
    // part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    check(part1(part1TestInput) == 4361)

    part1(finalInput).println()

    // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    println(part2(part2TestInput))
    check(part2(part2TestInput) == 467835)

    part2(finalInput).println()
}

val directions = listOf(
    Pair(-1, -1),
    Pair(-1, 0),
    Pair(-1, 1),
    Pair(0, -1),
    Pair(0, 1),
    Pair(1, -1),
    Pair(1, 0),
    Pair(1, 1),
)

fun findNthOccurrenceOf(str: String, subStr: String, occurrence: Int, startingIndex: Int = 0) : Int {
    val currentOccurrence = str.indexOf(subStr, startingIndex)
    return when (occurrence) {
    1 -> if (subStr.length == 3 || currentOccurrence+subStr.length == str.length ) { currentOccurrence}
        else if (currentOccurrence+subStr.length < str.length && str[currentOccurrence+subStr.length].isDigit()) {
            findNthOccurrenceOf(str, subStr, occurrence, currentOccurrence+1)
        }
        else if (currentOccurrence-1 > 0 && str[currentOccurrence-1].isDigit()) {
            findNthOccurrenceOf(str, subStr, occurrence, currentOccurrence+1)
        } else currentOccurrence

        else -> findNthOccurrenceOf(str, subStr, occurrence-1, currentOccurrence+1)
    }
}

fun getGearValue(r: Int, c: Int, input: List<String>, directionIndex: Int, gearValues: List<Int>) : Int {
    if (gearValues.size > 2) return 0;
    if (directionIndex == 8){
        return if (gearValues.size < 2) {
            0
        } else {
            gearValues.first() * gearValues.last()
        }
    }
    val direction = directions[directionIndex]
    val adjacentRow = r + direction.first
    val adjacentColumn = c + direction.second

    val testObject = getGearNumber(adjacentRow, adjacentColumn, input, gearValues)

    return getGearValue(r, c, testObject.input, directionIndex+1, testObject.gearValues)
}

fun getGearNumber(r: Int, c: Int, input: List<String>, gearValues: List<Int>) : TestObject =
    if (r >= 0 && r < input.size && c >= 0 && c < input.first().length && input[r][c].isDigit()) {
        val touching = input[r][c]
        val right = if (c+1 in 0..<input[0].length && input[r][c+1].isDigit()) input[r][c + 1] else ' '
        val left = if (c-1 in 0..<input[0].length && input[r][c-1].isDigit()) input[r][c- 1] else ' '
        val farRight = if (right != ' ' && c+2 in 0..<input[0].length && input[r][c + 2].isDigit()) input[r][c + 2] else ' '
        val farLeft = if (left != ' ' && c-2 in 0..<input[0].length && input[r][c - 2].isDigit()) input[r][c - 2] else ' '

        val modifiedInput = input.mapIndexed {i, row ->
            if (i == r) {
                row.mapIndexed { currentColumn, value ->
                    if (right != ' ' && currentColumn == c + 1) {
                        '.'
                    } else if (farRight != ' ' && currentColumn == c + 2) {
                        '.'
                    } else if (left != ' ' && currentColumn == c - 1) {
                        '.'
                    } else if (farLeft != ' ' && currentColumn == c - 2) {
                        '.'
                    } else value
                }.joinToString("")
            } else row
        }

        TestObject(
            input = modifiedInput,
            gearValues = gearValues + "$farLeft$left$touching$right$farRight".filter { it != ' ' }.toInt(),
        )
    } else TestObject(input, gearValues)

data class TestObject(
    val input: List<String>,
    val gearValues: List<Int>,
)