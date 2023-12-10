fun main() {
    fun part1(input: List<String>) : Long {
        val times = input[0].split(':').last().split(' ').mapNotNull {
            val trimmed = it.trim()
            if (trimmed.isEmpty()) null else it.toLong()
        }
        val distances = input[1].split(':').last().split(' ').mapNotNull {
            val trimmed = it.trim()
            if (trimmed.isEmpty()) null else it.toLong()
        }

        return times.mapIndexed { i, time ->
            val target = distances[i]
            (0..time).sumOf {
                val waiting = it
                val distanceTravelled = (time - waiting) * waiting
                if (distanceTravelled > target) 1L else 0L
            }
        }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>) : Long {
        val time = input[0].split(':').last().filter { it != ' ' }.toLong()
        val distance = input[1].split(':').last().filter { it != ' ' }.toLong()

        val list = (1L..time).toList()
        val bounds = list.mapNotNull {
            val waiting1 = it-1
            val distanceTravelled1 = (time - waiting1) * waiting1

            val waiting2 = it
            val distanceTravelled2 = (time - waiting2 * waiting2)

            if (distance in (distanceTravelled1 + 1)..<distanceTravelled2) {
                it
            }
            else if (distance in (distanceTravelled2 + 1)..<distanceTravelled1) {
                it
            }
            else null
        }

        return bounds.last().minus(bounds.first())+1
    }

    val currentDay = "6"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
//    val part1TestInput = readInput("day$currentDay/Test1")
//    println(part1(part1TestInput))
//    check(part1(part1TestInput) == 288L)
//    part1(finalInput).println()
    // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    println(part2(part2TestInput))
    check(part2(part2TestInput) == 71503L)
    part2(finalInput).println()
}
