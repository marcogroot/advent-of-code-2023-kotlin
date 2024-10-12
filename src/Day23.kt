import Utils.readInput
import Utils.println
import Utils.directions

fun main() {
    fun part1(input: List<String>): Long {
        val start = Pair(0, 1)
        val end = Pair(input.size - 1, input.first().length - 2)

        val visited: List<Pair<Int, Int>> = emptyList()
        var answer = 0L
        fun dfs(curr: Pair<Int, Int>, steps: Long, visited: List<Pair<Int, Int>>) {
            if (curr == end) {
                answer = Math.max(answer, steps)
            }

            val nextDirection = when (input[curr.first][curr.first]) {
                '>' -> directions[0]
                '<' -> directions[3]
                'v' -> directions[1]
                '^' -> directions[2]
                else -> null
            }

            if (visited.contains(nextDirection)) return
            if (nextDirection != null) {
                dfs(nextDirection, steps + 1, visited + curr)
            } else {
                directions.forEach {
                    val newRow = curr.first + it.first
                    val newCol = curr.second + it.second
                    if (newRow >= 0 && newCol >= 0 && newRow < input.size && newCol < input.first().length) {
                        if (visited.contains(Pair(newRow, newCol))) return
                        if (input[newRow][newCol] != '#') {
                            dfs(Pair(newRow, newCol), steps + 1, visited + curr)
                        }
                    }
                }
            }
            return
        }
        dfs(start, 0, visited)
        return answer
    }

        fun part2(input: List<String>): Long {
            return 0
        }

        val currentDay = "23"
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