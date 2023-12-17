fun main() {
    fun part1(input: List<String>) : Long =
        input[0].indices.map { c -> input.indices.map { r -> input[r][c] } }
            .sumOf{ getScore(0, 0, 0, it) }

    fun part2(input: List<String>) : Long {
        val mapRef = input.map {
            it.toMutableList()
        }.toMutableList()

        val rowSize = mapRef.size
        val colSize = mapRef.first().size

        fun shiftMap(index: Int, reversed: Boolean, vertical: Boolean) : Unit {
            var next = if (reversed) mapRef.first().size-1 else 0
            val increment = if (reversed) -1 else 1
            val indices = if (reversed) mapRef.indices.reversed() else mapRef.indices
            indices.map { curr ->
                val current = if (vertical) mapRef[curr][index] else mapRef[index][curr]
                val scoreGained = if (vertical) (rowSize - next).toLong() else (colSize - next).toLong()
                when(current) {
                    '.' ->  Unit
                    '#' -> {
                        next = curr+increment
                    }
                    else -> {
                        val stoneFell = if (reversed) (colSize-curr).toLong() > scoreGained else (colSize-curr).toLong() < scoreGained
                        if (stoneFell) {
                            if (vertical) {
                                mapRef[next][index] = 'O'
                                mapRef[curr][index] = '.'
                            } else {
                                mapRef[index][next] = 'O'
                                mapRef[index][curr] = '.'
                            }
                            next += increment
                        } else next = curr+increment
                    }
                }
            }
        }

        fun getScore2(index: Int) : Long {
            val a = mapRef.indices.sumOf { curr ->
                val current = mapRef[curr][index]
                if (current == 'O') (mapRef.size - curr).toLong()
                else 0
            }
            return a
        }

        fun performCycle() {
            mapRef.first().indices.map { shiftMap(it, reversed = false, vertical = true) }
            mapRef.indices.map { shiftMap(it, reversed = false, vertical = false) }
            mapRef.first().indices.map { shiftMap(it, reversed = true, vertical = true) }
            mapRef.indices.map { shiftMap(it, reversed = true, vertical = false) }
        }

        var previous = 0L
        (1..10000000).mapNotNull { count ->
            performCycle()
            val currentScore = mapRef.first().indices.sumOf{ getScore2(it) }
            if (previous == currentScore && count > 1000) return (currentScore)
            previous = currentScore
        }
        return -1
    }

    val currentDay = "14"
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

private tailrec fun getScore(curr: Int, next: Int, score: Long, col: List<Char>) : Long {
    if (curr == col.size) return score
    val scoreGained: Long = (col.size - next).toLong()

    return when(col[curr]) {
        '.' ->  getScore(curr+1, next, score, col)
        '#' ->  getScore(curr+1, curr+1, score, col)
        else -> {
            val stoneFell = (col.size-curr).toLong() < scoreGained
            getScore(curr+1, if (stoneFell) next+1 else curr+1, score+scoreGained, col)
        }
    }
}




