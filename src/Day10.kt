fun main() {
    fun part1(input: List<String>) : Int {
        var data = input.map {
            List(it.length) { 0 }.toMutableList()
        }.toMutableList()

        val start = input.mapIndexed { r, row ->
            row.mapIndexedNotNull { c, value -> 
                if (value == 'S') {
                    Pair(r, c)
                } else null
            }
        }.flatten().first()

        input.forEach { println(it) }
        println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
        fun isValid(r: Int, c: Int) : Boolean =
            r >= 0 && r < input.size && c >= 0 && c < input[0].length

        fun dfs(r: Int, c: Int, previousR: Int, previousC: Int, distance: Int) : Unit {
            val current = input[r][c]
//            data.forEach { println(it) }
//            println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
            data[r][c] = maxOf(distance, data[r][c])

            if (current == 'S' && distance != 0) return

            val directions = pipes[current]!!
            directions.forEach { direction ->
                val newRow = r + direction.first
                val newCol = c + direction.second
                if (isValid(newRow, newCol) && (newRow != previousR || newCol != previousC)) {
                    dfs(newRow, newCol, r, c, distance + 1)
                }
            }
        }

        dfs(start.first, start.second, -1, -1, 0)
        return (data[start.first][start.second] + 1) / 2
    }

    fun part2(input: List<String>) : Int  {
        return 0
    }

    val currentDay = "10"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
    part1(finalInput).println()
////   )  // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part2TestInput)
//    part2(finalInput).println()
}

val pipes: Map<Char, List<Pair<Int, Int>>> = mapOf(
    '|' to listOf(Pair(1, 0), Pair(-1, 0)),
    '-' to listOf(Pair(0, 1), Pair(0, -1)),
    'L' to listOf(Pair(-1, 0), Pair(0, 1)),
    'J' to listOf(Pair(-1, 0), Pair(0, -1)),
    '7' to listOf(Pair(1, 0), Pair(0, -1)),
    'F' to listOf(Pair(1, 0), Pair(0, 1)),
    '.' to emptyList(),
    'S' to listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)),
)