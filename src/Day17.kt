import java.util.PriorityQueue
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) : Long = getAnswer(input, 3)

    fun part2(input: List<String>) : Long = getAnswer(input, 10,4)

    val currentDay = "17"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
    part2(finalInput).println()
//
}

private val directions: List<Pair<Long, Long>> = listOf(
    Pair(0L, 1L),
    Pair(1L, 0L),
    Pair(-1L, 0L),
    Pair(0, -1),
)

val oppositeDirections: Map<Pair<Long, Long>, Pair<Long, Long>> = mapOf(
    Pair(1L, 0L) to Pair(-1L, 0L),
    Pair(-1L, 0L) to Pair (1L, 0L),
    Pair(0L, 1L) to Pair(0L, -1L),
    Pair(0L, -1L) to Pair(0L, 1L),
)

data class Node(
    val coordinates: Pair<Long, Long>,
    val direction: Pair<Long, Long>,
    val streak: Long,
    val score: Long,
)
fun Node.toState(): State = State(coordinates = coordinates, direction = direction, streak = streak)

data class State(
    val coordinates: Pair<Long, Long>,
    val direction: Pair<Long, Long>,
    val streak: Long,
)

private fun getAnswer(input: List<String>, consecutiveMax: Long, consecutiveMin: Long? = null) : Long {
    val visited = mutableMapOf<State, Long>()
    val endCoordinates = Pair(input.size-1.toLong(), input.first().length-1.toLong())
    fun isValid(r: Long, c: Long) : Boolean = (r in input.indices && c in input.first().indices)

    val queue = PriorityQueue<Node> { node1, node2 ->
        (node1.score-node2.score).toInt()
    }

    queue.add(
        Node(
            Pair(0, 1),
            Pair(0, 1),
            0,
            0,
        )
    )
    queue.add(
        Node(
            Pair(1, 0),
            Pair(1, 0),
            0,
            0,
        )
    )

    var bestAnswer = Long.MAX_VALUE

    while (queue.isNotEmpty()) {
        val curr = queue.peek()
        queue.remove()
        val coords = curr.coordinates
        val newScore = curr.score + input[coords.first.toInt()][coords.second.toInt()].digitToInt()

        if (curr.coordinates == endCoordinates) {
            bestAnswer = minOf(bestAnswer, newScore)
            continue
        }

        for (direction in directions) {
            if (direction == curr.direction && curr.streak == consecutiveMax-1L) continue
            if (direction == oppositeDirections[curr.direction]) continue
            if (consecutiveMin != null && (curr.streak < consecutiveMin-1) && direction != curr.direction) continue

            val newR = coords.first+direction.first
            val newC = coords.second+direction.second

            if (isValid(newR, newC)) {
                val newNode =  Node(
                    Pair(newR, newC),
                    direction,
                    if (direction==curr.direction) curr.streak+1 else 0,
                    newScore,
                )
                val newState = newNode.toState()
                if (visited.containsKey(newState)) continue
                visited[newState] = newScore
                queue.add(newNode)
            }
        }
    }
    return bestAnswer
}
