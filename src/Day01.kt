fun main() {
    fun part1(input: List<String>): Int {
        return input.splitBy { it.isEmpty() }
            .map { it.map { s -> s.toInt() } }
            .maxOfOrNull { it.fold(0) { acc, curr -> acc + curr } } ?: throw IllegalArgumentException()
    }

    fun part2(input: List<String>): Int {
        return input.splitBy { it.isEmpty() }
            .asSequence()
            .map { it.map { s -> s.toInt() } }
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
