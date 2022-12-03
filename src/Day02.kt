fun main() {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(" ") }
            .map { Pair(RockPaperScissors.fromABCSyntax(it[0]), RockPaperScissors.fromXYZSyntax(it[1])) }
            .map { it.second.totalScore(it.first) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(" ") }
            .map { Pair(RockPaperScissors.fromABCSyntax(it.first()), it[1]) }
            .map { Pair(it.first, it.first.determineMove(it.second)) }
            .map { it.second.totalScore(it.first) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class RockPaperScissors(private val s: String, private val s1: String, private val worth: Int) {
    ROCK("A", "X", 1),
    PAPER("B", "Y", 2),
    SCISSORS("C", "Z", 3);

    private fun score(other: RockPaperScissors): Int {
        if (this == other) {
            return 3
        }
        if (winsFrom(other)) {
            return 6
        }
        return 0
    }

    private fun winningMove(): RockPaperScissors {
        return when (this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
    }

    private fun losingMove(): RockPaperScissors {
        return when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }

    fun determineMove(shouldWin: String): RockPaperScissors {
        return when (shouldWin) {
            "X" -> this.losingMove()
            "Y" -> this
            "Z" -> this.winningMove()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    private fun winsFrom(other: RockPaperScissors): Boolean {
        return (this == ROCK && other == SCISSORS ||
                this == PAPER && other == ROCK ||
                this == SCISSORS && other == PAPER)
    }

    fun totalScore(other: RockPaperScissors): Int {
        return score(other) + this.worth
    }

    companion object {

        fun fromABCSyntax(s: String): RockPaperScissors {
            return enumValues<RockPaperScissors>()
                .first { it.s == s }
        }

        fun fromXYZSyntax(s: String): RockPaperScissors {
            return enumValues<RockPaperScissors>()
                .first { it.s1 == s }
        }
    }
}
