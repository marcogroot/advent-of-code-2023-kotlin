import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Long {
        val splits = listOf(-1) + input.mapIndexedNotNull{ index, row ->
            index.takeIf{ row.isEmpty()  }
        }

        return splits.windowed(2, 1) { input.slice(it.first()+1..<it.last())
        }.sumOf { getScore(it) }
    }

    fun part2(input: List<String>) : Long {
        val splits = listOf(-1) + input.mapIndexedNotNull{ index, row ->
            index.takeIf{ row.isEmpty()  }
        }

        return splits.windowed(2, 1) { input.slice(it.first()+1..<it.last())
        }.sumOf { getScore2(it) }
    }

    val currentDay = "13"
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

private fun getScore(map: List<String>, ) : Long {
    val verticalValue = (map.indices).windowed(2, 1) {
        val top = it.first()
        val bottom = it.last()
        if (isVerticallySymmetrical(map, top, bottom)) {
            ((top.toLong()+1) * 100L)
        } else null
    }.firstNotNullOfOrNull { it }
    if (verticalValue != null) return verticalValue
    return (map.first().indices).windowed(2, 1) {
        val left = it.first()
        val right = it.last()
        if (isHorizontallySymmetrical(map, left, right)) {
           left.toLong()+1
        } else null
    }.firstNotNullOf { it  }
}

private tailrec fun isVerticallySymmetrical(map: List<String>, top: Int, bottom: Int) : Boolean {
    if (top == -1 || bottom == map.size)  {
        return true
    }

    val topRow = map[top]
    val bottomRow = map[bottom]

    return if (topRow == bottomRow) {
       isVerticallySymmetrical(map, top-1, bottom+1)
    } else false
}

private tailrec fun isHorizontallySymmetrical(map: List<String>, left: Int, right: Int) : Boolean {
    if (left == -1 || right == map[0].length) return true

    val leftColumn = map.getColumn(left)
    val rightColumn = map.getColumn(right)
    return if (leftColumn == rightColumn) {
        isHorizontallySymmetrical(map, left-1, right+1)
    } else false
}

fun List<String>.getColumn(col: Int) : String = this.indices.map {
    this[it][col]
}.joinToString()

private fun getScore2(map: List<String>, ) : Long {
    val verticalValue = (map.indices).windowed(2, 1) {
        val top = it.first()
        val bottom = it.last()
        if (isVerticallySymmetrical2(map, top, bottom, false)) {
            ((top.toLong()+1) * 100L)
        } else null
    }.firstNotNullOfOrNull { it }
    if (verticalValue != null) return verticalValue
    return (map.first().indices).windowed(2, 1) {
        val left = it.first()
        val right = it.last()
        if (isHorizontallySymmetrical2(map, left, right, false)) {
            left.toLong()+1
        } else null
    }.firstNotNullOf { it  }
}

private tailrec fun isVerticallySymmetrical2(map: List<String>, top: Int, bottom: Int, changed: Boolean) : Boolean {
    if (top == -1 || bottom == map.size) {
        return changed
    }

    val topRow = map[top]
    val bottomRow = map[bottom]

    return if (topRow == bottomRow) {
        isVerticallySymmetrical2(map, top-1, bottom+1, changed)
    } else if (!changed && oneOff(topRow, bottomRow)) {
        isVerticallySymmetrical2(map, top-1, bottom+1, true)
    } else false
}

private tailrec fun isHorizontallySymmetrical2(map: List<String>, left: Int, right: Int, changed: Boolean) : Boolean {
    if (left == -1 || right == map[0].length) return changed

    val leftColumn = map.getColumn(left)
    val rightColumn = map.getColumn(right)
    return if (leftColumn == rightColumn) {
        isHorizontallySymmetrical2(map, left-1, right+1, changed)
    } else if (!changed && oneOff(leftColumn, rightColumn)) {
        isHorizontallySymmetrical2(map, left-1, right+1,true)
    } else false
}

private fun oneOff(a: String, b: String) = a.zip(b).count { it.first != it.second } == 1
