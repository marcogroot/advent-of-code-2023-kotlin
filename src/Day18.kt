import java.io.File
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>) : Long {
        val instructions = input.map {
            val temp = it.split(' ')
            listOf(temp[0], temp[1])
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
            listOf(direction.toString(), distance.toString())
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
//    part2(part1TestInput).println()
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

private fun getAnswer(input: List<String>, instructions: List<List<String>>) : Long {
    val visited = mutableListOf<Pair<Long, Long>>()
    var instructionIndex: Int = 0
    tailrec fun digHole(curr: Pair<Long, Long>, step: Int, direction: Char) : Unit {
        if (!visited.contains(curr)) visited.add(curr)
        if (step == 0) {
            instructionIndex++
            if (instructionIndex == input.size) return
            val instruction = instructions[instructionIndex]
            return digHole(curr, instruction[1].toInt(), instruction[0].toCharArray().first())
        }
        val direct = directions[direction]!!
        val nextR = curr.first + direct.first
        val nextC = curr.second + direct.second

        digHole(Pair(nextR, nextC), step-1, direction)
    }

    digHole(Pair(0,0), instructions[0][1].toInt(), instructions[0][0].toCharArray().first())
    val perimeter = visited.size
    val area = visited.zip(visited.drop(1) + visited.take(1)) { a, b -> a.second * b.first - a.first * b.second }.sum().absoluteValue / 2

    val map = List<Char>(7) { '.' }
    val fullMap = List<List<Char>>(10) { map }

    val updatedMap = fullMap.mapIndexed { r, row ->
        row.mapIndexed { c, value ->
            if (Pair(r.toLong(), c.toLong()) in visited) '#' else value
        }
    }

    updatedMap.forEach{
        println(it)
    }
    return ((area.toLong() - perimeter / 2L + 1L)+perimeter)
}