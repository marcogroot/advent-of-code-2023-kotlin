fun main() {
    fun part1(input: List<String>) : Long {
        val processedInput = processInput(input)
        val workFlows = processedInput.first
        val xmases = processedInput.second

        tailrec fun dfs(curr: Xmas, instructionName: String) : Boolean {
            val currentInstruction = workFlows[instructionName]
            currentInstruction!!.forEach {
                if (it.pureJump && it.jump.length == 1) return it.jump.first() == 'A'
                if (it.pureJump) return dfs(curr, it.jump)
                val valueWeCareAbout = when(it.character) {
                    'x' -> curr.x
                    'm' -> curr.m
                    'a' -> curr.a
                     else -> curr.s
                }
                if (it.greater && valueWeCareAbout > it.value) {
                    if (it.jump.first() == 'A') return true
                    if (it.jump.first() == 'R') return false
                    return dfs(curr, it.jump)
                }
                if (!it.greater && valueWeCareAbout < it.value) {
                    if (it.jump.first() == 'A') return true
                    if (it.jump.first() == 'R') return false
                    return dfs(curr, it.jump)
                }
            }
            return false
        }

        return xmases.sumOf {
            if (dfs(it, "in")) it.total else 0
        }
    }

    fun part2(input: List<String>) : Long {
        val processedInput = processInput(input)
        val workFlows = processedInput.first

        val acceptedValueRanges = mutableListOf<XmasRange>()

        tailrec fun dfs(curr: XmasRange, workFlowName: String, instructionIndex: Int) {
            if (instructionIndex == workFlows[workFlowName]!!.size) return

            val instruction = workFlows[workFlowName]!![instructionIndex]

            if (instruction.pureJump && instruction.jump.length == 1)  {
                if (instruction.jump.first() == 'A') {
                    acceptedValueRanges.add(curr)
                    return
                } else return
            }
            if (instruction.pureJump) return dfs(curr, instruction.jump, 0)

            val oldLowerBound = when(instruction.character) {
                'x' -> curr.x.first
                'm' -> curr.m.first
                'a' -> curr.a.first
                else -> curr.s.first
            }

            val oldUpperBound = when(instruction.character) {
                    'x' -> curr.x.last
                    'm' -> curr.m.last
                    'a' -> curr.a.last
                    else -> curr.s.last
            }

            val goLow = if (instruction.greater) {
                curr.copy(
                x = if (instruction.character == 'x') oldLowerBound.. instruction.value else curr.x,
                m = if (instruction.character == 'm') oldLowerBound.. instruction.value else curr.m,
                a = if (instruction.character == 'a') oldLowerBound.. instruction.value else curr.a,
                s = if (instruction.character == 's') oldLowerBound.. instruction.value else curr.s,)}
            else {
                curr.copy(
                x = if (instruction.character == 'x') oldLowerBound..< instruction.value else curr.x,
                m = if (instruction.character == 'm') oldLowerBound..< instruction.value else curr.m,
                a = if (instruction.character == 'a') oldLowerBound..< instruction.value else curr.a,
                s = if (instruction.character == 's') oldLowerBound..< instruction.value else curr.s,)}

            val goHigh = if (instruction.greater) {
                curr.copy(
                    x = if (instruction.character == 'x') instruction.value+1.. oldUpperBound else curr.x,
                    m = if (instruction.character == 'm') instruction.value+1.. oldUpperBound else curr.m,
                    a = if (instruction.character == 'a') instruction.value+1.. oldUpperBound else curr.a,
                    s = if (instruction.character == 's') instruction.value+1.. oldUpperBound else curr.s,)}
            else {
                curr.copy(
                    x = if (instruction.character == 'x') instruction.value..oldUpperBound else curr.x,
                    m = if (instruction.character == 'm') instruction.value..oldUpperBound else curr.m,
                    a = if (instruction.character == 'a') instruction.value..oldUpperBound else curr.a,
                    s = if (instruction.character == 's') instruction.value..oldUpperBound else curr.s,)}

            if (instruction.greater) {
                if (instruction.jump.first() == 'A') {
                    acceptedValueRanges.add(goHigh)
                    dfs(goLow, workFlowName, instructionIndex+1)
                }
                else if (instruction.jump.first() == 'R') {
                    dfs(goLow, workFlowName, instructionIndex+1)
                }
                else {
                    dfs(goHigh, instruction.jump, 0)
                    dfs(goLow, workFlowName, instructionIndex+1)
                }
            }
            else {
                if (instruction.jump.first() == 'A') {
                    acceptedValueRanges.add(goLow)
                    dfs(goHigh, workFlowName, instructionIndex+1)
                }
                else if (instruction.jump.first() == 'R') {
                    dfs(goHigh, workFlowName, instructionIndex+1)
                }
                else {
                    dfs(goLow, instruction.jump, 0)
                    dfs(goHigh, workFlowName, instructionIndex+1)
                }
            }
        }

        dfs(XmasRange(
            1L..4000L,
            1L..4000L,
            1L..4000L,
            1L..4000L),
            "in",
            0
        )
        return acceptedValueRanges.toList().sumOf { ranges ->
            ranges.toList().fold(1L) { acc, range ->
                acc * (range.last-range.first+1)
            }
        }
    }

    val currentDay = "19"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
    part2(finalInput).println()
}

private data class Xmas(
    val x: Long,
    val m: Long,
    val a: Long,
    val s: Long,
    val total: Long,
)

private data class XmasRange(
    val x: LongRange,
    val m: LongRange,
    val a: LongRange,
    val s: LongRange,
)
private data class Instruction(
    val character: Char,
    val greater: Boolean,
    val value: Long,
    val jump: String,
    val pureJump: Boolean,
)

private fun processInput(input: List<String>) : Pair<Map<String, List<Instruction>>, List<Xmas>> {
    val workFlowIndex = input.mapIndexedNotNull { i, v ->
        if (v.isEmpty()) i else null
    }.first()

    val workFlows = input.mapIndexedNotNull { r, row ->
        if (r >= workFlowIndex) null
        else {
            val instructionString = row.substring(row.indexOf('{') + 1, row.length-1)
            val name = row.takeWhile { it != '{' }
            val instructsRaw = instructionString.split(',')
            val full = instructsRaw.map {
                if (it.length <= 4) {
                    Instruction('X', false, -1, it, true)
                } else {
                    val character = it[0]
                    val greater = it[1] == '>'
                    val temp = it.drop(2).split(':')
                    val value = temp.first().toLong()
                    val jump = temp.last()
                    Instruction(character, greater, value, jump, false)
                }
            }
            Pair(name, full)
        }
    }.associate { it.first to it.second }

    val xmases = input.mapIndexedNotNull { r, row ->
        if (r <= workFlowIndex) null
        else {
            val pure = row.filter { it.isDigit() || it == ',' }.split(',').map { it.toLong() }
            Xmas(pure[0], pure[1], pure[2], pure[3], pure.sum())
        }
    }

    return Pair(workFlows, xmases)
}

private fun XmasRange.toList() : List<LongRange> = listOf(this.x, this.m, this.a, this.s)
