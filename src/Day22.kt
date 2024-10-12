import Brick.Companion.GROUND
import Utils.println
import Utils.readInput

var bricks: List<Brick> = emptyList()

private fun List<Brick>.structurallySignificant(): List<Brick> =
    filter { brick -> brick.supporting.any { it.supportedBy.size == 1 } }

private fun List<Brick>.settle(): List<Brick> = buildList {
    this@settle.forEach { brick ->
        var current = brick
        do {
            var settled = false
            val supporters = filter { below -> below.canSupport(current) }
            if (supporters.isEmpty() && !current.onGround()) {
                val restingPlace = filter { it.z.last < current.z.first - 1 }
                    .maxOfOrNull { it.z.last }?.let { it + 1 } ?: GROUND
                current = current.fall(restingPlace)
            } else {
                settled = true
                supporters.forEach { below -> below.supports(current) }
                add(current)
            }
        } while (!settled)
    }
}

private fun Brick.topple(): Set<Brick> = buildSet {
    add(this@topple)
    val untoppled = (bricks - this).toMutableSet()
    do {
        val willFall = untoppled
            .filter { it.supportedBy.isNotEmpty() }
            .filter { it.supportedBy.all { brick -> brick in this } }
            .also {
                untoppled.removeAll(it)
                addAll(it)
            }
    } while (willFall.isNotEmpty())
}

data class Brick(val id: Int, val x: IntRange, val y: IntRange, val z: IntRange) : Comparable<Brick> {
    val supporting = mutableSetOf<Brick>()
    val supportedBy = mutableSetOf<Brick>()

    override fun compareTo(other: Brick): Int =
        z.first - other.z.first

    fun supports(other: Brick) {
        supporting += other
        other.supportedBy += this
    }

    fun canSupport(other: Brick): Boolean =
        x intersects other.x && y intersects other.y && z.last + 1 == other.z.first

    fun onGround(): Boolean =
        z.first == GROUND

    fun fall(restingPlace: Int): Brick =
        copy(
            z = restingPlace..(restingPlace + (z.last - z.first))
        )

    companion object {
        const val GROUND = 1
        fun of(index: Int, input: String): Brick =
            input.split("~")
                .map { side -> side.split(",").map { it.toInt() } }
                .let { lists ->
                    val left = lists.first()
                    val right = lists.last()
                    Brick(
                        index,
                        left[0]..right[0],
                        left[1]..right[1],
                        left[2]..right[2]
                    )
                }
    }
}

private infix fun IntRange.intersects(x: IntRange): Boolean = x.endInclusive > this.start

fun main() {
    fun getBricks(input: List<String>) :  List<Brick> = input
        .mapIndexed { index, row -> Brick.of(index, row) }
        .sorted()
        .settle()

    fun part1(input: List<String>) : Long {
        bricks = getBricks(input)
        return bricks.size - bricks.structurallySignificant().size.toLong()
    }

    fun part2(input: List<String>) : Long {
        bricks = getBricks(input)
        return bricks.structurallySignificant().sumOf { it.topple().size - 1 }.toLong()
    }

    val currentDay = "22"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    part2(part1TestInput).println()
    part2(finalInput).println()
}
