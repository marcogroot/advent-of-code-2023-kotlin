import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { game ->
            val semiSplit = game.split(':')
            val id = semiSplit.first().substring(5, 10.coerceAtMost(semiSplit.first().length)).toInt()
            val rounds = semiSplit.last().split(";")
            if (rounds.any { round ->
                    round.split(',').any {
                        val colour: String = if (it.contains("blue")) {
                            "blue"
                        } else if (it.contains("red")) {
                            "red"
                        } else if (it.contains("green")) {
                            "green"
                        } else "misc"
                        val number = it.replace(colour, "").replace(" ", "").toInt()
                        when (colour) {
                            "red" -> number > 12
                            "green" -> number > 13
                            "blue" -> number > 14
                            else -> false
                        }
                    }
                }) 0 else id
        }

    fun part1Refactored(input: List<String>): Int =
        input.withIndex().sumOf {
            val semiSplit = it.value.split(':')
            val game = semiSplit.last().replace(";", ",").replace(" ", "")
            if (tooManyCubes("red", 12, game) ||
                tooManyCubes("green", 13, game) ||
                tooManyCubes("blue", 14, game)) {
                0
            } else it.index + 1
        }

    fun part2(input: List<String>): Int =
        input.sumOf { game ->
            val games = game.split(':').last().replace(";", ",").replace(" ", "")
            getMinimumCubes("red", games) * getMinimumCubes("green", games) * getMinimumCubes("blue", games)
        }

    val currentDay = "2"
    // part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    check(part1(part1TestInput) == 8)


    val part1Input = readInput("day$currentDay/Final")
    part1(part1Input).println()

    // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    check(part2(part2TestInput) == 2286)

    val part2Input = readInput("day$currentDay/Final")
    part2(part2Input).println()
}

// helper functions
fun tooManyCubes(colour: String, max: Int, game: String): Boolean =
    game.split(',')
        .filter { it.contains(colour) }
        .map { it.replace(colour, "") }
        .maxOf { it.toInt() } > max

fun getMinimumCubes(colour: String, game: String): Int =
    game.split(',')
        .filter { it.contains(colour) }
        .map { it.replace(colour, "") }
        .maxOf { it.toInt() }
