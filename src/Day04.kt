fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map {
                it.split(",", "-")
                    .map { s -> s.toInt() }
            }
            .map { Assignments(it[0], it[1]) to Assignments(it[2], it[3]) }
            .count { it.first.oneFullyOverlapsOther(it.second) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map {
                it.split(",", "-")
                    .map { s -> s.toInt() }
            }
            .map { Assignments(it[0], it[1]) to Assignments(it[2], it[3]) }
            .count { it.first.hasOverlapWith(it.second) }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

class Assignments(private val start: Int, private val end: Int) {
    fun size(): Int =
        end - start

    private fun fullyOverlaps(other: Assignments): Boolean =
        other.start >= this.start && other.end <= this.end

    fun oneFullyOverlapsOther(other: Assignments): Boolean =
        if (other.size() >= this.size()) {
            other.fullyOverlaps(this)
        } else {
            this.fullyOverlaps(other)
        }

    fun hasOverlapWith(other: Assignments): Boolean {
        val range = this.start..this.end
        val otherRange = other.start..other.end
        return range.any { otherRange.contains(it) }
    }
}