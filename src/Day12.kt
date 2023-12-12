fun main() {
    fun part1(input: List<String>) : Int {
        return input.sumOf {
            val a = getCombinations(it)
            a
        }
    }
    //fun part2(input: List<String>) = getAnswer(input, 1000000L)

    val currentDay = "12"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    //part2(finalInput).println()
}

private fun getCombinations(row: String) : Int {
    val splitRow = row.split(' ')
    val sprockets = splitRow.first().reversed()
    val values = splitRow.last().split(',').map { it.toInt() }.reversed()

    fun isValidSprocket(finalSprocket: String) : Boolean {
        val updated = finalSprocket.replace('?', '.')
        val numbers = updated.split('.').mapNotNull { if (it.isNotEmpty()) it.length else null }
        return numbers == values
    }

    fun dfs(index: Int, valueIndex: Int, newSprocket: String) : Int {

        if(valueIndex == values.size) {
            return if (isValidSprocket(newSprocket)) 1 else 0
        }
        if (index >= sprockets.length) return 0


        if (sprockets[index] == '.') return dfs(index+1, valueIndex, newSprocket)

        val currentValue = values[valueIndex]

        if (index+currentValue > sprockets.length) return 0
        val currentRange = sprockets.substring(index, index+currentValue)
        //println("comparing $currentValue to $index - ${index+currentValue-1}  $currentRange")
        return if (currentRange.all {it == '#' || it == '?'}) {
            val temp = newSprocket.replaceRange(index, index+currentValue, "#".repeat(currentValue))
            //println(temp)
            val nextNumber = dfs(index+currentValue+1, valueIndex+1, temp)
            val keepGoing = dfs(
                index+1,
                valueIndex,
                newSprocket
            )
            keepGoing + nextNumber
        } else 0
    }

    return dfs(0, 0, sprockets)
}