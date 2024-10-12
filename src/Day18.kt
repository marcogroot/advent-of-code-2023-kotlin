import kotlin.math.absoluteValue
import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Long {
        val instructions = input.map {
            val temp = it.split(' ')
            Pair(temp[1].first(), temp[0].toLong())
        }
        return getAnswer(input, instructions)
    }

    fun part2(input: List<String>) : Long {
        val instructions = input.map {
            val temp = it.split(' ')
            val secret = temp[2]
            val distance = secret.drop(2).take(5).toLong(radix = 16)
            val lastChar = secret.takeLast(2).dropLast(1).toCharArray().first()
            val direction = hexaDirectionMap[lastChar]!!
            Pair(direction, distance)
        }

        return getAnswer(input, instructions)
    }

    val currentDay = "18"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    part2(part1TestInput).println()
    part2(finalInput).println()
}

private val directions = mapOf(
    'R' to Pair(0, 1),
    'L' to Pair(0, -1),
    'U' to Pair(-1, 0),
    'D' to Pair(1, 0),
)

private val hexaDirectionMap = mapOf(
    '0' to 'R',
    '1' to 'D',
    '2' to 'L',
    '3' to 'U',
)

private fun getAnswer(input: List<String>, instructions: List<Pair<Char, Long>>): Long {
    val coordinates = instructions.fold(listOf(Pair(0L, 0L))) { coords, instruction ->
        val direction = instruction.first
        val distance = instruction.second

        val r = when (direction) {
            'R' -> distance
            'L' -> -distance
            else -> 0
        }
        val c = when (direction) {
            'U' -> distance
            'D' -> -distance
            else -> 0
        }

        val newR = coords.last().first + r
        val newC = coords.last().second + c

        coords + Pair(newR, newC)
    }

    val perimeter = instructions.sumOf { it.second }
    val area = coordinates.zipWithNext { a, b -> a.second * b.first - a.first * b.second }.sum().absoluteValue / 2
    return (area - perimeter / 2L + 1L)+perimeter
}