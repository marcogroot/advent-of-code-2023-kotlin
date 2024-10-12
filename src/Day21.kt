fun main() {
    fun part1(input: List<String>, maximumSteps: Int) : Long {
        val start = input.mapIndexed { r, row ->
           row.mapIndexedNotNull { c, value ->
               if (value == 'S') Pair(r, c) else null
           }
        }.flatten().firstNotNullOf { it }

        val dp = MutableList<MutableList<MutableList<Boolean>>>(input.size) {
            MutableList(input.first().length) {
                MutableList(65) { false }
            }
        }

        val destinations : MutableSet<Pair<Int, Int>> = mutableSetOf()

        fun dfs(curr: Pair<Int, Int>, stepsLeft: Int) {
            if (stepsLeft == 0) {
                destinations.add(curr)
                return
            }
            if (dp[curr.first][curr.second][stepsLeft]) return


            dp[curr.first][curr.second][stepsLeft] = true

            directions.forEach {
                val newRow = curr.first + it.first
                val newCol = curr.second + it.second

                if (newRow >= 0 && newCol >= 0 && newRow < input.size && newCol < input.first().length) {
                    if (input[newRow][newCol] != '#') {
                        dfs(Pair(newRow, newCol), stepsLeft - 1)
                    }
                }
            }
        }

        dfs(start, maximumSteps)

//        println(destinations.sortedBy { it.second }.sortedBy { it.first })
        return destinations.size.toLong()
    }

    fun part2(input: List<String>) : Long {
        return 0
    }

    val currentDay = "21"
    val finalInput = readInput("day$currentDay/Final")
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput, 6).println()
    part1(finalInput, 64).println()


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