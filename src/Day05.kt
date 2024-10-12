import Utils.println
import Utils.readInput

fun main() {
    fun part1(input: List<String>) : Long {
        val seeds = input.first().split(':').last().split(' ').mapNotNull { if (it.isEmpty()) null else it.toLong() }
        val mappers = getMappers(input)

        return seeds.minOf { seed ->
            mappers.fold(seed) { mappedSeed, mapper ->
                mapper.getMappedValue(mappedSeed)
            }
        }
    }

    fun part2(input: List<String>) : Long {
        val mappers = getMappers(input)
        val oldSeedValues = input.first().split(':').last().split(' ').mapNotNull { if (it.isEmpty()) null else it.toLong() }

        return oldSeedValues.mapIndexedNotNull { i, seedsRange ->
            if (i % 2 != 0) {
                val startValue = oldSeedValues[i-1]
                (startValue..<startValue+seedsRange).minOf{
                    foldSeedWithMapping(mappers, it)
                }
            } else null
        }.minOf { it }
    }

//    val currentDay = "5"
//    val finalInput = readInput("day$currentDay/Final")
    // part 1
//    val part1TestInput = readInput("day$currentDay/Test1")
//    println(part1(part1TestInput))
//    check(part1(part1TestInput) == 35L)
//    part1(finalInput).println()
    // part 2
//    val part2TestInput = readInput("day$currentDay/Test2")
//    part2(finalInput).println()
}

private fun List<String>.getDataList(i1: Int, i2: Int) : List<RangeOffset> {
    val endRange = if (i2 == this.size) i2 else i2-1
    val dataRange = this.toTypedArray().copyOfRange((i1+1),endRange).toList().map { str ->
        str.split(' ').map {it.toLong()
    } }
    return dataRange.map {
        val startingRange = it[1]
        val offsetAmount = it[0] - startingRange
        val rangeLength = it[2]

        RangeOffset(offsetAmount, startingRange..<startingRange+rangeLength)
    }
}

private data class RangeOffset(
    val offset: Long,
    val range: LongRange,
)

private fun List<RangeOffset>.getMappedValue(x: Long) : Long {
    this.map { if (x in it.range) return x + it.offset }
    return x
}

private fun getMappers(input: List<String>) : List<List<RangeOffset>> {
    val i1 = input.indexOf("seed-to-soil map:")
    val i2 = input.indexOf("soil-to-fertilizer map:")
    val i3 = input.indexOf("fertilizer-to-water map:")
    val i4 = input.indexOf("water-to-light map:")
    val i5 = input.indexOf("light-to-temperature map:")
    val i6 = input.indexOf("temperature-to-humidity map:")
    val i7 = input.indexOf("humidity-to-location map:")
    val seedSoilMap = input.getDataList(i1, i2)
    val soilFertilizerMap = input.getDataList(i2, i3)
    val fertilizerWaterMap = input.getDataList(i3, i4)
    val waterLightMap = input.getDataList(i4, i5)
    val lightToTemperatureMap = input.getDataList(i5, i6)
    val temperatureToHumidityMap = input.getDataList(i6, i7)
    val humidityToLocationMap = input.getDataList(i7, input.size)
    return listOf(seedSoilMap, soilFertilizerMap, fertilizerWaterMap, waterLightMap, lightToTemperatureMap, temperatureToHumidityMap, humidityToLocationMap)
}

private fun foldSeedWithMapping(mappers: List<List<RangeOffset>>, seed: Long) : Long =
    mappers.fold(seed) { mappedSeed, mapper -> mapper.getMappedValue(mappedSeed) }
