import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Int {
        val instructions = input[0]
        val mappings = input.mapIndexedNotNull { i, row ->
            when (i) {
                in 0..1 -> {
                    null
                } else -> {
                    val spots = row.filter { it.isLetter() || it == ' ' }.split(' ').mapNotNull { if (it.isEmpty()) null else it.trim() }
                    spots.first() to Pair(spots[1], spots[2])
                }
            }
        }.toMap()

        tailrec fun dfs(current: String, instructionIndex: Int, score: Int) : Int {
            if (current == "ZZZ") return score
            val instruction = instructions[instructionIndex%(instructions.length)]
            val right = instruction == 'R'
            val newValue = if (right) {
                mappings[current]!!.second
            } else mappings[current]!!.first
            return(dfs(newValue, instructionIndex+1, score+1))
        }

        return dfs("AAA", 0, 0)
    }

    fun part2(input: List<String>) : Long {
        val instructions = input[0]
        val mappings = input.mapIndexedNotNull { i, row ->
            when (i) {
                in 0..1 -> {
                    null
                } else -> {
                val spots = row.filter { it.isLetterOrDigit() || it == ' ' }.split(' ').mapNotNull { if (it.isEmpty()) null else it.trim() }
                spots.first() to Pair(spots[1], spots[2])
            }
            }
        }.toMap()

        val points = mappings.keys.mapNotNull { if (it[2] == 'A') it else null }

        val lcms = points.map { point ->
            tailrec fun findFirstZ(current: String, instructionIndex: Int) : String {
                if (current[2] == 'Z') return current
                val instruction = instructions[instructionIndex%(instructions.length)]
                val right = instruction == 'R'
                val newValue = if (right) {
                    mappings[current]!!.second
                } else mappings[current]!!.first
                return(findFirstZ(newValue, instructionIndex+1))
            }

            val firstZ = findFirstZ(point, 0)
            tailrec fun findLoopSize(current: String, instructionIndex: Int, score: Long, flag: Boolean = true) : Long {
                if (current[2] == 'Z' && flag) return score
                val instruction = instructions[instructionIndex%(instructions.length)]
                val right = instruction == 'R'
                val newValue = if (right) {
                    mappings[current]!!.second
                } else mappings[current]!!.first
                return(findLoopSize(newValue, instructionIndex+1, score+1))
            }
            findLoopSize(firstZ, 0, 0, false)
        }
        return findLCMOfListOfNumbers(lcms)
    }

    val currentDay = "8"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    println(part1(part1TestInput))
    part1(finalInput).println()
////   )  // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    println(part2(part2TestInput))
    part2(finalInput).println()
}

private fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

private fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}
