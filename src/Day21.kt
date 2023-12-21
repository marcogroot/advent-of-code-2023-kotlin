import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>, maximumSteps: Int) : Long {
        val start = input.mapIndexed { r, row ->
           row.mapIndexedNotNull { c, value ->
               if (value == 'S') Pair(r, c) else null
           }
        }.flatten().firstNotNullOf { it }

        val visited = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
        val queue : Queue<Step> = LinkedList()
        queue.add(Step(start, maximumSteps))
        visited[maximumSteps] = mutableSetOf(start)
        var turn = maximumSteps
        while (queue.isNotEmpty()) {
            val curr = queue.peek()
            queue.remove()
            if (curr.number == 0) {
                break
            }
            if (curr.number < turn) {
                turn = curr.number

                println("current turn is $turn")
            }
            if (visited.containsKey(curr.number)) {
                visited[curr.number]!!.add(curr.coordinates)
            } else visited[curr.number] = mutableSetOf<Pair<Int, Int>>(curr.coordinates)

            directions.forEach { direction ->
                val adjacentRow = curr.coordinates.first + direction.first
                val adjacentColumn = curr.coordinates.second + direction.second
                val newCoord = Pair(adjacentRow, adjacentColumn)
                if (adjacentRow in input.indices && adjacentColumn in input.first().indices && newCoord !in visited[curr.number]!! && input[adjacentRow][adjacentColumn] != '#') {
                    queue.add(Step(newCoord, curr.number-1))
                }
            }
        }
        return queue.toSet().size.toLong()
    }

    fun part2(input: List<String>) : Long {
        return 0
    }

    val currentDay = "21"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput, 6).println()
    part1(finalInput, 64).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
//    part2(finalInput).println()
}

private data class Step(
    val coordinates: Pair<Int, Int>,
    val number: Int,
)

private val directions = listOf(
    Pair(0, 1),
    Pair(1, 0),
    Pair(-1, 0),
    Pair(0, -1),
)