import kotlin.math.sign

fun main() {

    fun part1(headSteps: List<Point>): Int {
        return determineTailSteps(headSteps).distinct().size
    }

    fun part2(headSteps: List<Point>): Int {
        return (1..9).fold(headSteps) { acc, _ ->
            determineTailSteps(acc)
        }.distinct().size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val testStepInstructions = parseStepInstructions(testInput)
    val testHeadSteps = determineHeadSteps(testStepInstructions)

    val testInputGold = readInput("Day09_test_gold")
    val testStepInstructionsGold = parseStepInstructions(testInputGold)
    val testHeadStepsGold = determineHeadSteps(testStepInstructionsGold)

    check(part1(testHeadSteps) == 13)
    check(part2(testHeadSteps) == 1)
    check(part2(testHeadStepsGold) == 36)

    val input = readInput("Day09")
    val stepInstructions = parseStepInstructions(input)
    val headSteps = determineHeadSteps(stepInstructions)

    println(part1(headSteps))
    println(part2(headSteps))
}

private fun determineTailSteps(headSteps: List<Point>): List<Point> {
    val tailSteps = mutableListOf(Point(0, 0))
    headSteps.forEach {
        val lastTail = tailSteps.last()
        if (!lastTail.touches(it)) {
            tailSteps.add(lastTail.moveTowards(it))
        }
    }
    return tailSteps
}

private fun parseStepInstructions(testInput: List<String>) = testInput.map {
    val instructionString = it.split(" ")
    StepInstruction(instructionString[0], instructionString[1].toInt())
}

private fun determineHeadSteps(instructions: List<StepInstruction>): List<Point> {
    val init = mutableListOf(Point(0, 0))
    val headSteps = instructions.fold(init) { acc, instruction ->
        acc.addAll(acc.last().step(instruction))
        acc
    }
    return headSteps
}

private data class StepInstruction(val direction: String, val steps: Int)

private fun Point.step(stepInstruction: StepInstruction): List<Point> {
    return when (stepInstruction.direction) {
        "U" -> (0 until stepInstruction.steps).fold(mutableListOf()) { acc, _ ->
            val p = acc.lastOrNull() ?: this
            acc.add(p.copy(y = p.y - 1))
            acc
        }

        "D" -> (0 until stepInstruction.steps).fold(mutableListOf()) { acc, _ ->
            val p = acc.lastOrNull() ?: this
            acc.add(p.copy(y = p.y + 1))
            acc
        }

        "L" -> (0 until stepInstruction.steps).fold(mutableListOf()) { acc, _ ->
            val p = acc.lastOrNull() ?: this
            acc.add(p.copy(x = p.x - 1))
            acc
        }

        "R" -> (0 until stepInstruction.steps).fold(mutableListOf()) { acc, _ ->
            val p = acc.lastOrNull() ?: this
            acc.add(p.copy(x = p.x + 1))
            acc
        }

        else -> throw IllegalArgumentException("Direction ${stepInstruction.direction} not known")
    }
}

private fun Point.moveTowards(other: Point): Point =
    Point(
        (other.x - x).sign + x,
        (other.y - y).sign + y
    )