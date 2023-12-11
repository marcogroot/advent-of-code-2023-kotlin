import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) : Int {
        val start = input.mapIndexed { r, row ->
            row.mapIndexedNotNull { c, value -> 
                if (value == 'S') {
                    Pair(r, c)
                } else null
            }
        }.flatten().first()

        tailrec fun dfs(r: Int, c: Int, previousR: Int, previousC: Int, distance: Int) : Int {
            val current = input[r][c]

            val directions = pipes[current]!!
            val nextDirection = directions.firstNotNullOf { direction ->
                val newRow = r + direction.first
                val newCol = c + direction.second
                if (newRow != previousR || newCol != previousC) {
                    Pair(newRow, newCol)
                } else null
            }

            return if (current == 'S' && distance != 0) { return distance }
            else dfs(nextDirection.first, nextDirection.second, r, c, distance+1)
        }
        return (dfs(start.first, start.second, -1, -1, 0) + 1) / 2
    }

    fun part2(input: List<String>) : Int {
        val start = input.mapIndexed { r, row ->
            row.mapIndexedNotNull { c, value ->
                if (value == 'S') {
                    Pair(r, c)
                } else null
            }
        }.flatten().first()

        tailrec fun dfsForPipe(r: Int, c: Int, previousR: Int, previousC: Int, currentPipe: List<Pair<Int, Int>>) : List<Pair<Int, Int>> {
            val current = input[r][c]

            val directions = pipes[current]!!
            val nextDirection = directions.firstNotNullOf { direction ->
                val newRow = r + direction.first
                val newCol = c + direction.second
                if (newRow != previousR || newCol != previousC) {
                    Pair(newRow, newCol)
                } else null
            }

            return if (current == 'S' && currentPipe.isNotEmpty()) { return currentPipe }
            else dfsForPipe(nextDirection.first, nextDirection.second, r, c, currentPipe+Pair(r, c))
        }

        val pipeValues = dfsForPipe(start.first, start.second, -1, -1, emptyList())
        return (pipeValues.sumOf {

                var leftVals = 0
                var rightVals = 0

                for (i in it.second..0) {
                    val currentCoord = Pair(it.first, i)
                    leftVals = if (currentCoord in pipeValues) {
                        abs(it.second - i)-1
                    } else 0
                }
                for (i in it.second..input[0].length) {
                    val currentCoord = Pair(it.first, i)
                    rightVals = if (currentCoord in pipeValues) {
                        abs(it.second - i)-1
                    } else 0
                }

                leftVals+rightVals
        })/2

    }

    val currentDay = "10"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
//    part1(finalInput).println()
////   )  // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part2TestInput).println()
    part2(finalInput).println()
}

private val pipes: Map<Char, List<Pair<Int, Int>>> = mapOf(
    '|' to listOf(Pair(1, 0), Pair(-1, 0)),
    '-' to listOf(Pair(0, 1), Pair(0, -1)),
    'L' to listOf(Pair(-1, 0), Pair(0, 1)),
    'J' to listOf(Pair(-1, 0), Pair(0, -1)),
    '7' to listOf(Pair(1, 0), Pair(0, -1)),
    'F' to listOf(Pair(1, 0), Pair(0, 1)),
    '.' to emptyList(),
    'S' to listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)),
)

private val cardinalDirections: List<Pair<Int, Int>> = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

data class EnclosedTiles(
    val size: Int,
    val isEnclosed: Boolean,
)