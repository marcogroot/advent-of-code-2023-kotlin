fun main() {

    fun part1(input: List<String>) : Int =
    input.sumOf { str ->
        val firstDigit = str.find { char -> char.isDigit() }.toString()
        val lastDigit = str.findLast { char -> char.isDigit() }.toString()
        firstDigit.plus(lastDigit).toInt()
    }

    fun part2(input: List<String>): Int =
            input.sumOf { str ->
                val orderedListOfDigitOccurrences = str.mapIndexedNotNull { i, char -> getDigitAtIndexOrNull(i, str) }
                orderedListOfDigitOccurrences.first().plus(orderedListOfDigitOccurrences.last()).toInt()
            }

    // test if implementation meets criteria from the description, like:
    val currentDay = "1"
    val part1TestInput = readInput("day$currentDay/Part1Test")
    check(part1(part1TestInput) == 142)

    val part2TestInput = readInput("day$currentDay/Part2Test")
    check(part2(part2TestInput) == 281)

    val part1Input = readInput("day$currentDay/Part1")
    part1(part1Input).println()

    val part2Input = readInput("day$currentDay/Part2")
    part2(part2Input).println()
}

// helper functions and objects
fun getDigitAtIndexOrNull(i: Int, str: String) : String? {
    if (str[i].isDigit()) return str[i].toString()

    numbers.map { num ->
        if (i.plus(num.length) <= str.length) {
            val substr = str.substring(i, i+num.length)
            if (substr == num) return numberMap[substr]
        }
    }

    return null
}

val numbers = listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
)
val numberMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
)
