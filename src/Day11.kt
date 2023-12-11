import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) = getAnswer(input, 2L)
    fun part2(input: List<String>) = getAnswer(input, 1000000L)

    val currentDay = "11"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    part2(finalInput).println()
}
private fun getAnswer(input: List<String>, expansionMultiplier: Long) : Long {
    val rowsToExpand = input.indices.filter { r -> input[r].all { it == '.' } }
    val colsToExpand = input[0].indices.filter { c -> input.indices.all { r -> input[r][c] == '.' } }
    val points = input.mapIndexed { r, row ->
        row.mapIndexedNotNull { c, value ->
            if (value == '#') Pair(r.toLong(), c.toLong()) else null
        }
    }.flatten()

    val updatedPoints = points.map { pair ->
        val rowIncrease = (rowsToExpand.count { it < pair.first }) * (expansionMultiplier - 1)
        val colIncrease = (colsToExpand.count { it < pair.second }) * (expansionMultiplier - 1)
        Pair(pair.first + rowIncrease, pair.second + colIncrease)
    }

    return updatedPoints.sumOf { p1 ->
        updatedPoints.sumOf { p2 ->
            if (p1 != p2) {
                getDistance(p1, p2)
            } else 0
        }
    } / 2L
}

private fun getDistance(p1: Pair<Long, Long>, p2: Pair<Long, Long>) : Long =
    abs(p1.first-p2.first) + abs(p1.second-p2.second)
