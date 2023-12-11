import kotlin.math.abs
import kotlin.math.absoluteValue

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

        val cycle = dfsForPipe(start.first, start.second, -1, -1, emptyList())
        val perimeter = cycle.size
        val area = cycle.zip(cycle.drop(1) + cycle.take(1)) { a, b -> a.second * b.first - a.first * b.second }.sum().absoluteValue / 2
        return  area - perimeter / 2 + 1
    }

    val currentDay = "10"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
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
