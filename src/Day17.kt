fun main() {
    fun part1(input: List<String>) : Int {
        val visited = mutableMapOf<Node, Int>()

        fun dfs(curr: Point, currentDirection: Pair<Int, Int>, streak: Int, total: Int) : Unit {
            if (total > 110) return
            val r = curr.r
            val c = curr.c
            if (r < 0 || c < 0 || r >= input.size || c >= input.first().length || streak >= 3) return

            val currentSquare = input[r][c].digitToInt()
            val pointsAtSquare = currentSquare+total
            println("visiting $r $c with score $pointsAtSquare")
            val currentNode = Node(curr, currentDirection, streak)

            if (visited.containsKey(currentNode)) {
                val existingScore = visited[currentNode]!!
                if (existingScore < pointsAtSquare) return
            }
            visited[currentNode] = pointsAtSquare
            if (r == input.size-1 && c == input.first().length-1) return

            val oppositeDirection = oppositeDirections[currentDirection]!!
            return directions.minus(oppositeDirection).forEach {
                val newR = r + it.first
                val newC = c + it.second
                val newPoint = Point(newR, newC)
                 if (it == currentDirection) {
                    dfs(newPoint, currentDirection, streak+1, pointsAtSquare)
                 } else dfs(newPoint, currentDirection, 0, pointsAtSquare)
            }
        }

        dfs(Point(0, 0), Pair(0, 1), 0, 0)

        val a = input.mapIndexed { r, row ->
           row.mapIndexed { c, value ->
              val bestNodes = visited.keys.filter { it.point == Point(r, c) }
               bestNodes.mapNotNull {
                   if (visited.containsKey(it)) {
                       visited[it]
                   } else null
               }.minOf { it }
           }
        }

        a.forEach{
            println(it)
        }
        val bestNode = visited.keys.filter {
            it.point == Point(input.size - 1, input.first().length - 1)
        }

        val b = bestNode.mapNotNull {
            if (visited.containsKey(it)) {
                visited[it]
            } else null
        }

        println(b)

        return 0
    }

    fun part2(input: List<String>) : Int {
        return 0
    }

    val currentDay = "17"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
//    part2(finalInput).println()
}

data class Point(
    val r: Int,
    val c: Int,
)

private val directions = listOf(
    Pair(0, 1),
    Pair(1, 0),
    Pair(-1, 0),
    //Pair(0, -1),
)

val oppositeDirections = mapOf(
    Pair(1, 0) to Pair(-1, 0),
    Pair(-1, 0) to Pair (1, 0),
    Pair(0, 1) to Pair(0, -1),
    Pair(0, -1) to Pair(0, 1),
)

private data class Node(
    val point: Point,
    val directions: Pair<Int, Int>,
    val streak: Int,
)