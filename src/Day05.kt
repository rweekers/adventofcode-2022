fun main() {

    fun part1(
        stacks: Map<Int, Stack<Char>>,
        instructions: List<Instruction>
    ): String {
        instructions
            .forEach { instruction ->
                repeat((1..instruction.times).count()) {
                    stacks[instruction.to]?.push(
                        stacks[instruction.from]?.pop() ?: throw IllegalArgumentException()
                    )
                }
            }

        return stacks
            .map { it.value.pop() }
            .fold("") { acc, c -> acc + c }
    }

    fun part2(
        stacks: Map<Int, Stack<Char>>,
        instructions: List<Instruction>
    ): String {
        instructions
            .forEach { instruction ->
                val t = stacks[instruction.from]?.pop(instruction.times) ?: throw IllegalArgumentException()
                val s = stacks[instruction.to] ?: throw IllegalArgumentException()
                s.push(t)
            }

        return stacks
            .map { it.value.pop() }
            .fold("") { acc, c -> acc + c }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val testSplitIndex = testInput.indexOfFirst { it.isEmpty() }
    val testPositionToIndex = determinePositionToIndex(testInput, testSplitIndex)
    val testInstructions = determineInstructions(testInput, testSplitIndex)

    check(part1(determineStacks(testPositionToIndex, testInput, testSplitIndex), testInstructions) == "CMZ")
    check(part2(determineStacks(testPositionToIndex, testInput, testSplitIndex), testInstructions) == "MCD")

    val input = readInput("Day05")
    val splitIndex = input.indexOfFirst { it.isEmpty() }
    val positionToIndex = determinePositionToIndex(input, splitIndex)
    val instructions = determineInstructions(input, splitIndex)

    println(part1(determineStacks(positionToIndex, input, splitIndex), instructions))
    println(part2(determineStacks(positionToIndex, input, splitIndex), instructions))
}

private fun determinePositionToIndex(input: List<String>, splitIndex: Int) =
    input.subList(splitIndex - 1, splitIndex)
        .first()
        .mapIndexed { index, s -> s.toString() to index }
        .filter { it.first.trim().isNotEmpty() }
        .map { it.first.toInt() to it.second }

private fun determineStacks(
    positionToIndex: List<Pair<Int, Int>>,
    input: List<String>,
    splitIndex: Int
): Map<Int, Stack<Char>> {
    val stacks = buildMap<Int, Stack<Char>> {
        positionToIndex.forEach {
            this[it.first] = Stack()
        }
    }

    input.subList(0, splitIndex - 1)
        .reversed()
        .forEach {
            positionToIndex
                .forEach { p ->
                    if (p.second <= it.length && it[p.second].toString().trim().isNotEmpty()) {
                        val characterToStore = it[p.second]
                        stacks[p.first]?.push(characterToStore)
                    }
                }
        }

    return stacks
}

private fun determineInstructions(input: List<String>, splitIndex: Int) =
    input.subList(splitIndex + 1, input.size)
        .map { it.split(" ") }
        .map { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) }

class Instruction(val times: Int, val from: Int, val to: Int)