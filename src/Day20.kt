import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>) : Long {
        val startingIndex = input.indexOfFirst { it.contains("broadcaster") }
        val startingModules = input[startingIndex].dropWhile { it != '>' }.drop(1).filter { it != ' ' }.split(',')
        val conjunctions = mutableListOf<String>()
        val modules = input.mapIndexedNotNull { r, it ->
            if (r != startingIndex) {
                val symbol = it.first()
                val name = it.takeWhile {it != ' '}.drop(1)
                val destinations = it.dropWhile { it != '>' }.drop(1).filter { it != ' ' }.split(',')
                if (symbol == '%') {
                    Pair(name, FlipFLop(State.OFF, destinations, name))
                } else {
                    conjunctions.add(name)
                    Pair(name, Conjunction(emptyMap(), destinations, name))
                }
            } else null
        }.associate { it.first to it.second }.toMutableMap()

        conjunctions.forEach { conjunct ->
            (modules[conjunct]!! as Conjunction).pulses = modules.filter { moduleMapEntry ->
                moduleMapEntry.value.destinations.contains(conjunct) }.entries.associate {
                it.key to Pulse.LOW
            }
        }

        var answer = 0L
        var lowPulseCount = 0L
        var highPulseCount = 0L
        modules.forEach {
            println(it)
        }
        fun pressButton() {
            val queue: Queue<Triple<String, Pulse, String>> = LinkedList()

            startingModules.forEach { queue.add(Triple(it, Pulse.LOW, "ABC")) }
            var lowPulsesSent = 0L
            var highPulsesSent = 0L

            while (queue.isNotEmpty()) {
                val curr = queue.peek()
                val module = modules[curr.first]!!
                val pulse = curr.second
                val source = curr.third
                queue.remove()
                if (module is FlipFLop) {
                    if (pulse == Pulse.HIGH) continue
                    when (module.state) {
                        State.ON -> {
                            val newModule = FlipFLop(State.OFF, module.destinations, module.name)
                            modules[module.name] = newModule
                            lowPulsesSent += module.destinations.size
                            module.destinations.forEach {
                                println("Sending ${module.name} -> LOW -> $it")
                                if (modules.containsKey(it)) queue.add(Triple(it, Pulse.LOW, module.name))
                            }
                        }
                        State.OFF -> {
                            val newModule = FlipFLop(State.ON, module.destinations, module.name)
                            modules[module.name] = newModule
                            highPulsesSent += module.destinations.size
                            module.destinations.forEach {
                                println("Sending ${module.name} -> high-> $it")
                                if (modules.containsKey(it)) queue.add(Triple(it, Pulse.HIGH, module.name))
                            }
                        }
                    }
                }
                else if (module is Conjunction) {
                    val updatedPulses  = module.pulses.entries.associate {
                            if (it.key == source) {
                                it.key to pulse
                            } else it.key to it.value
                        }
                    //println(updatedPulses)
                    val pulseToSend = if (updatedPulses.values.all { it == Pulse.HIGH })  Pulse.LOW else Pulse.HIGH
                    val newModule = Conjunction(updatedPulses, module.destinations, module.name)
                    modules[module.name] = newModule
                    if (pulseToSend == Pulse.HIGH) {
                        highPulsesSent += module.destinations.size
                    } else lowPulsesSent += module.destinations.size
                    module.destinations.forEach {
                        println("Sending ${module.name} -> $pulseToSend -> $it")
                        if (modules.containsKey(it)) queue.add(Triple(it, pulseToSend, module.name))
                    }
                }
            }
            lowPulseCount += lowPulsesSent
            highPulseCount += highPulsesSent
        }


        for (i in 0..999) {
            pressButton()
        }
        val buttonPulses = (startingModules.size+1)*1000L
        return (lowPulseCount+buttonPulses) * highPulseCount
    }


    fun part2(input: List<String>) : Long {
        return 0
    }

    val currentDay = "20"
    val finalInput = readInput("day$currentDay/Final")
//  part 1
    val part1TestInput = readInput("day$currentDay/Test1")
//    part1(part1TestInput).println()
    part1(finalInput).println()
//  part 2
    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(part1TestInput).println()
//    part2(finalInput).println()
}

enum class State { ON, OFF }
enum class Pulse{ LOW, HIGH }

private abstract class Module(
    open val destinations: List<String>,
    open val name: String,
)

private data class FlipFLop(
    val state: State,
    override val destinations: List<String>,
    override val name: String,
) : Module(destinations, name)

private data class Conjunction(
    var pulses: Map<String, Pulse>,
    override val destinations: List<String>,
    override val name: String,
) : Module(destinations, name)
