fun main() {

    val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun part1(input: List<String>): Int {
        return input
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
            .map { it.first.commonCharacter(it.second) }
            .sumOf { alphabet.indexOf(it) + 1 }
    }

    fun part2(input: List<String>): Int {
        return input
            .windowed(3, 3)
            .map { it[0].commonCharacters(it[1]).commonCharacter(it[2]) }
            .sumOf { alphabet.indexOf(it) + 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}