fun main() {
    fun part1(input: List<String>) : Int = input.sumOf { getCombinations(it) }

    fun part2(input: List<String>) : Long = input.sumOf {
        getCombinations2(it)
    }


    val currentDay = "12"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    part2(part2TestInput).println()
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

    tailrec fun dfs(index: Int, valueIndex: Int, newSprocket: String) : Int {
        if(valueIndex == values.size) { return if (isValidSprocket(newSprocket)) 1 else 0 }
        if (index >= sprockets.length) return 0
        if (sprockets[index] == '.') return dfs(index+1, valueIndex, newSprocket)
        val currentValue = values[valueIndex]
        if (index+currentValue > sprockets.length) return 0
        val currentRange = sprockets.substring(index, index+currentValue)
        val temp = newSprocket.replaceRange(index, index+currentValue, "#".repeat(currentValue))
        return if (currentRange.all {it == '#' || it == '?'}) {
                dfs(index+currentValue+1, valueIndex+1, temp) +
                dfs(index+1, valueIndex, newSprocket)
        } else dfs(index+1, valueIndex, newSprocket
        )
    }
    return dfs(0, 0, sprockets)
}

private fun getCombinations2(row: String) : Long {
//    val splitRow = row.split(' ')
//    val sprocketsWithExcess = (splitRow.first().reversed() +'?').repeat(5)
//    val sprocket = sprocketsWithExcess.take(sprocketsWithExcess.length-1)
//    val values = (splitRow.last()+',').repeat(5).split(',').mapNotNull { if (it.isNotEmpty()) it.toInt() else null}.reversed()
    val splitRow = row.split(' ')
    val sprocket = splitRow.first().reversed()
    val values = splitRow.last().split(',').map { it.toInt() }.reversed()
    fun dfs(valueIndex: Int, newSprocket: String, fromJump: Boolean) : Long {
        if(valueIndex == values.size) {
            return 1
        }
        val currentValue = values[valueIndex]

        if (newSprocket.isBlank()) return 0
        if (newSprocket.first() == '#' && fromJump) { return 0 }
        if (currentValue > newSprocket.length) { return 0 }
        val m = ((values.size-valueIndex)*2)-2
        if (newSprocket.length < m) return 0;

        val nextResult = dfs(valueIndex, newSprocket.drop(1), false)
        val currentRange = newSprocket.substring(0, 0+currentValue)

        val nextNumberResult = if (currentRange.all {it == '#' || it == '?'}) {
            val opt1 = newSprocket.drop(currentValue)
            val opt2 = if (opt1.isNotEmpty() && opt1.first() == '?') opt1.replaceFirst('?', '.') else opt1
            dfs(valueIndex+1, opt2, true)
        } else 0

        return nextNumberResult+nextResult
    }

    return dfs(0, sprocket, false)
}