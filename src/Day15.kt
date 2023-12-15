fun main() {
    fun part1(input: List<String>) : Long =
        input.first().split(',').map { str ->
            str.getBoxNumber()
        }.sumOf { it.toLong()}


    fun part2(input: List<String>) : Long {
        val operations = input.first().split(',').map { instruction ->
            val box = instruction.takeWhile { it != '=' && it != '-' }
            val equals = instruction.contains('=')
            val valueString = instruction.mapNotNull { c ->
                if (c.isDigit()) c else null
            }.joinToString()
            Operation(box, equals, if(valueString.isEmpty()) null else valueString.toInt())
        }

        tailrec fun applyOperations(index: Int, boxes: List<List<Box>>) : Long {
            if (index == operations.size) {
                return boxes.mapIndexed { r, row ->
                    row.mapIndexed { c, box ->
                        val a = ((1+r) * (c+1) * box.value).toLong()
                        println("${1+r} ${c+1} ${box.value}")
                        a
                    }.sumOf { it }
                }.sumOf { it }
            }

            val current = operations[index]
            val boxNumber = current.name.getBoxNumber()

            return when (current.equals) {
                true -> {
                    if (boxes[boxNumber].find { it.name == current.name} != null) {
                        val newBoxes = boxes.map { row ->
                            row.map { box -> if (box.name == current.name) Box(current.name, current.value!!) else box }
                        }
                        applyOperations(index+1, newBoxes)
                    } else  {
                        val newBoxes = boxes.mapIndexed { i, it -> if (i == boxNumber) it+listOf(Box(current.name, current.value!!)) else it }
                        applyOperations(index+1, newBoxes)
                    }
                }
                false -> {
                    val newBoxes = boxes.map { row ->
                        row.mapNotNull { box -> if (box.name == current.name) null else box }
                    }
                    applyOperations(index+1, newBoxes)
                }
            }
        }

       val emptyBoxList: List<List<Box>> = List(256) { emptyList() }
       return applyOperations(0, emptyBoxList)
    }

    val currentDay = "15"
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

private data class Operation(
    val name: String,
    val equals: Boolean,
    val value: Int?,
)

private data class Box(
    val name: String,
    val value: Int,
)
private fun String.getBoxNumber() : Int =
    this.fold(0) { acc, c ->
        val currentValue = acc+c.code
    (currentValue*17).mod(256)
}

