import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>) : Long {
        val startingModules = input.first().dropWhile { it != '>' }.drop(1).filter { it != ' ' }.split(',')

        val modules = input.mapIndexedNotNull { r, it ->
            if (r > 0) {
                val symbol = it.first()
                val name = it.takeWhile {it != ' '}
                val destinations = it.dropWhile { it != '>' }.drop(1).filter { it != ' ' }.split(',')
                if (symbol == '%') {
                    Pair(name, FlipFLop(State.OFF, destinations))
                } else Pair(name, Conjunction(false, destinations))
            } else null
        }.associate { it.first to it.second }.toMutableMap()

        var answer = 0

        fun pressButton() {
            val queue: Queue<Pair<String, Pulse>> = LinkedList()

            startingModules.forEach { queue.add(Pair(it, Pulse.LOW)) }

            while (queue.isNotEmpty()) {
                val curr = queue.peek()
                val module = modules[curr.first]!!
                val pulse = curr.second

                queue.remove()

                if (module is FlipFLop) {
                    if (pulse == Pulse.HIGH) continue
                    when (module.state) {
                        State.ON -> {

                        }
                        State.OFF -> {
                            val newModule = FlipFLop()
                        }
                    }
                }
                else {

                }
            }
        }


        for (i in 0..999) {
            pressButton()
        }
        return answer
    }


    fun part2(input: List<String>) : Long {
        return 0
    }

    val currentDay = "20"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
    part1(part1TestInput).println()
//    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
//    part2(finalInput).println()
}
private enum class State { ON, OFF }
private enum class Pulse{ LOW, HIGH }
private abstract class Module(
    open val destinations: List<String>,
)

private data class FlipFLop(
    val state: State,
    override val destinations: List<String>,
) : Module(destinations)

private data class Conjunction(
    val mostRecentPulse: Pulse,
    override val destinations: List<String>,
) : Module(destinations)