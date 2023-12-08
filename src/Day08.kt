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


        var current = "AAA"
        var x = 0
        var instructionIndex = 0
        while (current != "ZZZ") {
            x++
            val instruction = instructions[instructionIndex]
            instructionIndex++
            if (instructionIndex == instructions.length) instructionIndex = 0
            val right = instruction == 'R'
            if (right) {
                current = mappings[current]!!.second
            } else current = mappings[current]!!.first
        }
        return x
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

        var points = mappings.keys.mapNotNull { if (it[2] == 'A') it else null }

        var lcms = points.map { point ->
            var current = point
            var instructionIndex = 0
            var x = 0L
            while (current[2] != 'Z') {
                x++
                val instruction = instructions[instructionIndex]
                instructionIndex++
                if (instructionIndex == instructions.length) instructionIndex = 0
                val right = instruction == 'R'
                if (right) {
                    current = mappings[current]!!.second
                } else current = mappings[current]!!.first
            }

            x = 0
            var flag = true
            while (current[2] != 'Z' || flag) {
                flag = false
                x++
                val instruction = instructions[instructionIndex]
                instructionIndex++
                if (instructionIndex == instructions.length) instructionIndex = 0
                val right = instruction == 'R'
                if (right) {
                    current = mappings[current]!!.second
                } else current = mappings[current]!!.first
            }
            x
        }

        return findLCMOfListOfNumbers(lcms)
    }

    val currentDay = "8"
    val finalInput = readInput("day$currentDay/Final")
//     part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    println(part1(part1TestInput))
////    check(part1(part1TestInput== 288L)
//    part1(finalInput).println()
////   )  // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    println(part2(part2TestInput))
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
