import java.security.spec.ECField

fun main() {
//    fun part1(input: List<String>): Int {
//        val validNumberArray = input.mapIndexed { r, row ->
//            row.mapIndexed { c, _ ->
//                directions.map { direction ->
//                    val adjacentRow = r + direction.first
//                    val adjacentColumn = c + direction.second
//                    if (adjacentRow >= 0 && adjacentRow < input.size && adjacentColumn >= 0 && adjacentColumn < row.length) {
//                        input[adjacentRow][adjacentColumn] != '.' && !input[adjacentRow][adjacentColumn].isDigit()
//                    } else false
//                }.any { it }
//            }
//        }
//
//        return input.mapIndexed { r, row ->
//            val numbersInRow = row.map { if (!it.isDigit()) '.' else it }.joinToString("")
//                .split('.').filter { it.isNotEmpty() }
//
//            val numberOccurrences = numbersInRow.groupingBy { it }.eachCount()
//
//            numberOccurrences.map {
//                val occurrences = 1..it.value
//                val number = it.key
//                occurrences.map { occurrence ->
//                    val startIndexOfNumber = findNthOccurenceOf(row, number, occurrence)
//                    val numberRange = startIndexOfNumber..<startIndexOfNumber + number.length
//                    if (numberRange.map { c ->
//                            validNumberArray[r][c]
//                        }.any { validIndex -> validIndex }) {
//                        println("adding $number")
//                        number.toInt()
//                    } else {
//                        0
//                    }
//                }
//            }.sumOf { number -> number.sumOf { it } }
//        }.sumOf { it }
//    }

    fun part2(input: List<String>): Int =
        input.mapIndexed { r, row ->
            row.mapIndexed { c, _ ->
                if (row[c] == '*') {
                    val touchingArr = directions.map { direction ->
                        val adjacentRow = r + direction.first
                        val adjacentColumn = c + direction.second
                        if (adjacentRow >= 0 && adjacentRow < input.size && adjacentColumn >= 0 && adjacentColumn < row.length) {
                            input[adjacentRow][adjacentColumn].isDigit()
                        } else false}

                        if (touchingArr.count { it } >= 2) {
                            getGearValue(r, c, input)
                        } else 0
                } else 0
            }
        }.sumOf { number -> number.sumOf { it } }

//    fun part2(input: List<String>): Int =
    val currentDay = "3"
    val finalInput = readInput("day$currentDay/Final")
    // part 1
//    val part1TestInput = readInput("day$currentDay/Test1")
//    check(part1(part1TestInput) == 4361)

//    part1(finalInput).println()

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

fun findNthOccurenceOf(str: String, subStr: String, occurrence: Int, startingIndex: Int = 0) : Int {
    val currentOccurrence = str.indexOf(subStr, startingIndex)
    return when (occurrence) {
    1 -> if (subStr.length == 3 || currentOccurrence+subStr.length == str.length ) { currentOccurrence}
        else if (currentOccurrence+subStr.length < str.length && str[currentOccurrence+subStr.length].isDigit()) {
            findNthOccurenceOf(str, subStr, occurrence, currentOccurrence+1)
        }
        else if (currentOccurrence-1 > 0 && str[currentOccurrence-1].isDigit()) {
            findNthOccurenceOf(str, subStr, occurrence, currentOccurrence+1)
        } else currentOccurrence

        else -> findNthOccurenceOf(str, subStr, occurrence-1, currentOccurrence+1)
    }
}

fun getGearValue(r: Int, c: Int, input: List<String>, gearNumbers: List<Int> = emptyList()) : Int {
    if (gearNumbers.size == 2) return gearNumbers.first() * gearNumbers.last()

    val testObject :TestObject? = directions.map { direction ->
        val adjacentRow = r + direction.first
        val adjacentColumn = c + direction.second
        if (adjacentRow >= 0 && adjacentRow < input.size && adjacentColumn >= 0 && adjacentColumn < input[0].length) {
            if (input[adjacentRow][adjacentColumn] != '.' && !input[adjacentRow][adjacentColumn].isDigit()) {
                getIntTouchingIndex(adjacentRow, adjacentColumn, input)
            } else null
        } else null
    }.first()

    getGearValue(r, c, compressedInput, gearNumbers)
}

fun getIntTouchingIndex(r: Int, c: Int, input: List<String>) : TestObject {
    val touching = input[r][c]
    val right = if (c+1 < input[0].length && input[r][c+1].isDigit()) input[r][c+1]  else ' '
    val left = if (c-1 > -1 && input[r][c-1].isDigit()) input[r][c-1]  else ' '
    return TestObject(
        gearNumber = "$left$touching$right".filter { it != ' '}.toInt(),
        emptyRow = r,
        emptyCol = c-1,
        emptyCol2 = c+1
    )
}

data class TestObject(
    val gearNumber: Int,
    val emptyRow: Int,
    val emptyCol: Int,
    val emptyCol2: Int,
)