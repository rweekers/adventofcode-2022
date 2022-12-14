import kotlin.math.absoluteValue

fun main() {

    fun part1(signalStrengths: List<Int>): Int {
        val cycles = listOf(20, 60, 100, 140, 180, 220)

        return cycles.fold(mutableListOf<Int>()) { acc, curr ->
            acc.add(signalStrengths[curr - 1] * curr)
            acc
        }.sum()
    }

    fun part2(signalStrengths: List<Int>) {
        val pixelList = signalStrengths.mapIndexed { index, signal ->
            (signal - index % 40).absoluteValue <= 1
        }.map { if (it) "#" else "." }
        pixelList.windowed(40, 40) { row ->
            row.forEach { pixel -> print(pixel) }
            println()
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val testCpuInstructions = determineCpuInstructions(testInput)
    val testSignalStrengths = determineSignalStrengths(testCpuInstructions)
    check(part1(testSignalStrengths) == 13140)
    // no check for part 2, visual check

    val input = readInput("Day10")
    val cpuInstructions = determineCpuInstructions(input)
    val signalStrengths = determineSignalStrengths(cpuInstructions)
    println(part1(signalStrengths))
    println(part2(signalStrengths))
}

private fun determineCpuInstructions(input: List<String>): List<CpuInstruction> {
    return input.map {
        val rawInstruction = it.split(" ")
        val isNoop = rawInstruction[0] == "noop"
        val increment = if (isNoop) 0 else rawInstruction[1].toInt()
        val cycles = if (isNoop) 1 else 2
        CpuInstruction(increment, cycles)
    }
}

private fun determineSignalStrengths(input: List<CpuInstruction>) =
    input.fold(mutableListOf(1)) { acc, curr ->
        val lastSignalStrength = acc.last()
        if (curr.cycles > 1) {
            acc.add(lastSignalStrength)
        }
        acc.add(lastSignalStrength + curr.increment)
        acc
    }


private data class CpuInstruction(val increment: Int, val cycles: Int)
