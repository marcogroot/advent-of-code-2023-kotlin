import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Int = input.sumOf { getCombinations(it) }

    fun part2(input: List<String>) : Long {
//        val splitRow = row.split(' ')
//        val sprocketsWithExcess = (splitRow.first().reversed() +'?').repeat(5)
//        val sprocket = sprocketsWithExcess.take(sprocketsWithExcess.length-1)
//        val values = (splitRow.last()+',').repeat(5).split(',').mapNotNull { if (it.isNotEmpty()) it.toInt() else null}.reversed()
        val mem = mutableMapOf<Pair<Int, String>, Long>()

        val answer = input.sumOf { row ->
            val splitRow = row.split(' ')
            val sprocket = splitRow.first().reversed()
            val values = splitRow.last().split(',').map { it.toInt() }.reversed()

            fun isValidSprocket(finalSprocket: String) : Long {
                val numbers = finalSprocket.split('.').mapNotNull { if (it.isNotEmpty()) it.length else null }
                return if (numbers == values) 1L else 0L
            }

            fun dfs(index: Int, curr: String) : Long {
                val firstQuestionMark = curr.indexOf('?', index)
                if (firstQuestionMark == -1) { return isValidSprocket(curr) }

                val hashString = curr.replaceRange(firstQuestionMark,firstQuestionMark+1,"$")
                val dotString = curr.replaceRange(firstQuestionMark,firstQuestionMark+1,".")

                val hashKey = Pair(firstQuestionMark+1, hashString)
                val dotKey = Pair(firstQuestionMark+1, dotString)

                val hashValue = if (mem.containsKey(hashKey)) {
                    mem[hashKey]!!.toLong()
                } else dfs(firstQuestionMark+1, hashString)

                val dotValue = if (mem.containsKey(dotKey)) {
                    mem[dotKey]!!.toLong()
                } else dfs(firstQuestionMark+1, dotString)

                mem[hashKey] = hashValue
                mem[dotKey] = dotValue

                return hashValue + dotValue
            }

            val firstQuestionMark = sprocket.indexOfFirst { it == '?' }
            val a = if (firstQuestionMark == -1) isValidSprocket(sprocket) else dfs(0, sprocket)
            a

        }
        return answer
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
//    part2(finalInput).println()
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
            dfs(index+currentValue+1, valueIndex+1, temp) + dfs(index+1, valueIndex, newSprocket)
        } else dfs(index+1, valueIndex, newSprocket
        )
    }
    return dfs(0, 0, sprockets)
}