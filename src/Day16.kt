import kotlin.math.max

fun main() {
    fun part1(input: List<String>) : Long {
        val visited: MutableSet<Pair<Pair<Int, Int>, DIRECTIONS>> = mutableSetOf()

        tailrec fun dfs(
            current: Pair<Int, Int>,
            direction: DIRECTIONS,
        ) : Set<Pair<Pair<Int, Int>, DIRECTIONS>> {
            val r = current.first
            val c = current.second
            if (r < 0 || c < 0 || r >= input.size || c >= input.first().length || Pair(current, direction) in visited) return visited

            val currentSymbol = input[r][c]

            visited.add(Pair(current, direction))
            val newR= current.first+directions[direction]!!.first
            val newC = current.second+directions[direction]!!.second

            return when (currentSymbol) {
                '|' -> {
                    when (direction) {
                        DIRECTIONS.LEFT,
                        DIRECTIONS.RIGHT -> dfs(Pair(r+1, c),DIRECTIONS.DOWN) +
                            dfs(Pair(r-1, c), DIRECTIONS.UP)
                        DIRECTIONS.UP -> dfs(Pair(r-1, c), DIRECTIONS.UP)
                        DIRECTIONS.DOWN -> dfs(Pair(r+1, c),DIRECTIONS.DOWN)
                    }

                }
                '-' -> {
                    when (direction) {
                        DIRECTIONS.LEFT -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                        DIRECTIONS.RIGHT -> dfs(Pair(r, c+1), DIRECTIONS.RIGHT)
                        DIRECTIONS.UP,
                        DIRECTIONS.DOWN ->
                            dfs(Pair(r, c+1), DIRECTIONS.RIGHT) +
                                dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                    }
                }
                '/' -> {
                    when (direction) {
                        DIRECTIONS.LEFT -> dfs(Pair(r+1, c), DIRECTIONS.DOWN)
                        DIRECTIONS.RIGHT -> dfs(Pair(r-1, c), DIRECTIONS.UP)
                        DIRECTIONS.UP -> dfs(Pair(r, c+1), DIRECTIONS.RIGHT)
                        DIRECTIONS.DOWN -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                    }
                }
                '\\' -> {
                    when (direction) {
                        DIRECTIONS.LEFT ->  dfs(Pair(r-1, c), DIRECTIONS.UP)
                        DIRECTIONS.RIGHT ->  dfs(Pair(r+1, c), DIRECTIONS.DOWN)
                        DIRECTIONS.UP -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                        DIRECTIONS.DOWN ->   dfs(Pair(r, c+1), DIRECTIONS.RIGHT)

                    }
                }
                else -> {
                    dfs(Pair(newR, newC), direction)
                }
            }
        }

        dfs(Pair(0, 0), DIRECTIONS.RIGHT)
        val set = visited.toSet()
        return set.fold(setOf<Pair<Int, Int>>()) { acc, curr ->
            acc + curr.first
        }.size.toLong()
    }

    fun part2(input: List<String>) : Long {
        val edgeCoordinates = input.indices.mapNotNull {r ->
            input.first().indices.mapNotNull {c ->
                if (r == 0 || r == input.size-1 || c == 0 || c == input.first().length-1) Pair(r, c) else null
            }
        }.flatten()

        fun getAnswer(r: Int, c: Int, direction: DIRECTIONS) : Long  {
            val visited: MutableSet<Pair<Pair<Int, Int>, DIRECTIONS>> = mutableSetOf()
            tailrec fun dfs(
                current: Pair<Int, Int>,
                direction: DIRECTIONS,
                ) : Set<Pair<Pair<Int, Int>, DIRECTIONS>> {
                val r = current.first
                val c = current.second
                if (r < 0 || c < 0 || r >= input.size || c >= input.first().length || Pair(current, direction) in visited) return visited

                val currentSymbol = input[r][c]

                visited.add(Pair(current, direction))
                val newR= current.first+directions[direction]!!.first
                val newC = current.second+directions[direction]!!.second

                return when (currentSymbol) {
                    '|' -> {
                        when (direction) {
                            DIRECTIONS.LEFT,
                            DIRECTIONS.RIGHT -> dfs(Pair(r+1, c),DIRECTIONS.DOWN) +
                                dfs(Pair(r-1, c), DIRECTIONS.UP)
                            DIRECTIONS.UP -> dfs(Pair(r-1, c), DIRECTIONS.UP)
                            DIRECTIONS.DOWN -> dfs(Pair(r+1, c),DIRECTIONS.DOWN)
                        }

                    }
                    '-' -> {
                        when (direction) {
                            DIRECTIONS.LEFT -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                            DIRECTIONS.RIGHT -> dfs(Pair(r, c+1), DIRECTIONS.RIGHT)
                            DIRECTIONS.UP,
                            DIRECTIONS.DOWN ->
                                dfs(Pair(r, c+1), DIRECTIONS.RIGHT) +
                                    dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                        }
                    }
                    '/' -> {
                        when (direction) {
                            DIRECTIONS.LEFT -> dfs(Pair(r+1, c), DIRECTIONS.DOWN)
                            DIRECTIONS.RIGHT -> dfs(Pair(r-1, c), DIRECTIONS.UP)
                            DIRECTIONS.UP -> dfs(Pair(r, c+1), DIRECTIONS.RIGHT)
                            DIRECTIONS.DOWN -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                        }
                    }
                    '\\' -> {
                        when (direction) {
                            DIRECTIONS.LEFT ->  dfs(Pair(r-1, c), DIRECTIONS.UP)
                            DIRECTIONS.RIGHT ->  dfs(Pair(r+1, c), DIRECTIONS.DOWN)
                            DIRECTIONS.UP -> dfs(Pair(r, c-1), DIRECTIONS.LEFT)
                            DIRECTIONS.DOWN ->   dfs(Pair(r, c+1), DIRECTIONS.RIGHT)

                        }
                    }
                    else -> {
                        dfs(Pair(newR, newC), direction)
                    }
                }
            }

            dfs(Pair(r, c), direction)
            val set = visited.toSet()
            return set.fold(setOf<Pair<Int, Int>>()) { acc, curr ->
                acc + curr.first
            }.size.toLong()
        }

        val result = edgeCoordinates.maxOf {
            val r = it.first
            val c = it.second
            val score = if (r == 0 && c == 0) max(getAnswer(r, c, DIRECTIONS.RIGHT), getAnswer(r, c, DIRECTIONS.DOWN))
            else if (r == 0 && c == input.first().length-1) max(getAnswer(r, c, DIRECTIONS.LEFT), getAnswer(r, c, DIRECTIONS.DOWN))
            else if (r == input.size-1 && c == 0) max(getAnswer(r, c, DIRECTIONS.RIGHT), getAnswer(r, c, DIRECTIONS.UP))
            else if (r == input.size-1 && c == input.first().length-1) max(getAnswer(r, c, DIRECTIONS.LEFT), getAnswer(r, c, DIRECTIONS.UP))
            else if (r == 0) getAnswer(r, c, DIRECTIONS.DOWN)
            else if (r == input.size-1) getAnswer(r, c, DIRECTIONS.UP)
            else if (c == 0) getAnswer(r, c, DIRECTIONS.RIGHT)
            else getAnswer(r, c, DIRECTIONS.LEFT)

            println("$r $c $score")
            score
        }



        return result
    }

    val currentDay = "16"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
    part2(finalInput).println()
}

private val directions = mapOf (
    DIRECTIONS.LEFT to Pair(0, -1),
    DIRECTIONS.RIGHT to Pair(0, 1),
    DIRECTIONS.UP to Pair(-1, 0),
    DIRECTIONS.DOWN to Pair(1, 0),
)

enum class DIRECTIONS {
    LEFT,
    RIGHT,
    DOWN,
    UP,
}