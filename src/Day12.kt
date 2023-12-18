//fun main() {
//    fun part1(input: List<String>) : Int = input.sumOf { getCombinations(it) }
//
//    fun part2(input: List<String>) : Long = input.sumOf {
//        getCombinations2(it)
//    }
//
//
//    val currentDay = "12"
//    val finalInput = readInput("day$currentDay/Final")
////  part 1
//    val part1TestInput = readInput("day$currentDay/Test1")
////    part1(part1TestInput).println()
////    part1(finalInput).println()
////  part 2
//    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part2TestInput).println()
//    //part2(finalInput).println()
//}
//
//private fun getCombinations(row: String) : Int {
//    val splitRow = row.split(' ')
//    val sprockets = splitRow.first().reversed()
//    val values = splitRow.last().split(',').map { it.toInt() }.reversed()
//
//    fun isValidSprocket(finalSprocket: String) : Boolean {
//        val updated = finalSprocket.replace('?', '.')
//        val numbers = updated.split('.').mapNotNull { if (it.isNotEmpty()) it.length else null }
//        return numbers == values
//    }
//
//    tailrec fun dfs(index: Int, valueIndex: Int, newSprocket: String) : Int {
//        if(valueIndex == values.size) { return if (isValidSprocket(newSprocket)) 1 else 0 }
//        if (index >= sprockets.length) return 0
//        if (sprockets[index] == '.') return dfs(index+1, valueIndex, newSprocket)
//        val currentValue = values[valueIndex]
//        if (index+currentValue > sprockets.length) return 0
//        val currentRange = sprockets.substring(index, index+currentValue)
//        val temp = newSprocket.replaceRange(index, index+currentValue, "#".repeat(currentValue))
//        return if (currentRange.all {it == '#' || it == '?'}) {
//                dfs(index+currentValue+1, valueIndex+1, temp) +
//                dfs(index+1, valueIndex, newSprocket)
//        } else dfs(index+1, valueIndex, newSprocket
//        )
//    }
//    return dfs(0, 0, sprockets)
//}
//
//private fun getCombinations2(row: String) : Long {
////    val splitRow = row.split(' ')
////    val sprocketsWithExcess = (splitRow.first().reversed() +'?').repeat(5)
////    val sprocket = sprocketsWithExcess.take(sprocketsWithExcess.length-1)
////    val values = (splitRow.last()+',').repeat(5).split(',').mapNotNull { if (it.isNotEmpty()) it.toInt() else null}.reversed()
//    val splitRow = row.split(' ')
//    val sprocket = splitRow.first().reversed()
//    val values = splitRow.last().split(',').map { it.toInt() }.reversed()
//
//    fun dfs(left: Int, right: Int, valueIndex: Int,) : Long {
//        if (right >= sprocket.length) return 0
//        if(valueIndex == values.size) { return 1 }
//        val currentValue = values[valueIndex]
//
//        val currentSprocket = sprocket.substring(left, right)
//        val nextChar = if (right+1 < sprocket.length) sprocket[right+1] else null
//        val currentRange = 0 until currentValue
//
//        val allSprockets = (currentSprocket.substring(currentRange).all {it == '#' || it == '?'}) && (nextChar == '.' || nextChar == '?')
//
//        val nextNumber = if (allSprockets) {
//            val newLeft =
//            val nextString = if (nextChar == '?')
//        }
//
//    }
//
//    return dfs(0, sprocket, false)
//}