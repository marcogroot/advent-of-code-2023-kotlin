fun main() {
    fun part1(input: List<String>): Int =

    fun part2(input: List<String>): Int =

    val currentDay = "3"
    // part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    check(part1(part1TestInput) == 8)


    val part1Input = readInput("day$currentDay/Final")
    part1(part1Input).println()

    // part 2
    val part2TestInput = readInput("day$currentDay/Test2")
    check(part2(part2TestInput) == 2286)

    val part2Input = readInput("day$currentDay/Final")
    part2(part2Input).println()
}
